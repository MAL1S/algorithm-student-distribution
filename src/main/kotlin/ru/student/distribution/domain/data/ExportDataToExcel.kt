package ru.student.distribution.domain.data

import com.grapecity.documents.excel.Workbook
import ru.student.distribution.model.Participation
import ru.student.distribution.model.Project
import ru.student.distribution.model.Student
import org.apache.poi.ss.usermodel.WorkbookFactory
import ru.student.distribution.model.Institute
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object ExportDataToExcel {

    fun writeProjectsWithStudents(
        students: List<Student>,
        notApplied: List<Student>,
        projects: List<Project>,
        participations: List<Participation>,
        institute: Institute,
        isUniformly: Boolean = false,
        filePath: String
    ) {
        val finalPath = if (isUniformly) "$filePath/равномерно_$institute.xlsx"
        else "$filePath/$institute.xlsx"

        val workBook = Workbook()
        //workBook.worksheets.add()
        var statIndex = 1
        val workSheetStats = workBook.worksheets.get(0)
        workSheetStats.name = "Статистика по проектам"
        workSheetStats.getRange("A$statIndex:F$statIndex").value =
            arrayOf("Название проекта", "ID проекта", "Укомплектованность", "Процент молчунов")

        statIndex++

        for (project in projects) {
            val partsCount = participations.count { it.projectId == project.id && it.stateId == 1 }
            //println(project)
            //println("$partsCount ${participations.count { it.projectId == project.id && it.priority == 5 }}")
            workSheetStats.getRange("A$statIndex:F$statIndex").value =
                arrayOf(project.title,
                    project.id,
                    partsCount,
                    if (partsCount == 0) "0%" else "${(participations.count { it.projectId == project.id && it.priority == 5 } / partsCount.toDouble() * 100).toInt()}%")
            statIndex++
        }

        workBook.worksheets.add()
        var studIndex = 1
        val workSheetStud = workBook.worksheets.get(1)
        workSheetStud.name = "Не зачисленные студенты"
        workSheetStud.getRange("A$studIndex:C$studIndex").value =
            arrayOf("ФИО", "Группа", "Номер зачетной книжки")
        studIndex++
        for (student in notApplied) {
            workSheetStud.getRange("A$studIndex:C$studIndex").value = arrayOf(
                student.name,
                student.fullGroupName,
                student.id
            )
            studIndex++
        }

        var index = 2
        for (project in projects) {
            workBook.worksheets.add()
            val workSheet = workBook.worksheets.get(index)

            val tempName = project.title
                .replace("?", "")
                .replace(":", "")
                .replace("/", " ")
            workSheet.name = "\"" + tempName
                .substring(0, if (tempName.length < 27) tempName.length else 27) + "\"${index - 1}"

            val projectParticipations = participations.filter { it.projectId == project.id && it.stateId == 1 }
            var participationIndexExcel = 1
            workSheet.getRange("A$participationIndexExcel:E$participationIndexExcel").value =
                arrayOf("ID проекта", "Название", "Заказчик", "Руководители", "Группы")
            participationIndexExcel++

            workSheet.getRange("A$participationIndexExcel:E$participationIndexExcel").value =
                arrayOf(
                    project.id,
                    project.title,
                    project.customer,
                    project.supervisors.toString().replace("[", "").replace("]", ""),
                    project.groups.toString().replace("[", "").replace("]", "")
                )
            participationIndexExcel++

            workSheet.getRange("A$participationIndexExcel:E$participationIndexExcel").value =
                arrayOf("ФИО", "Группа", "Номер зачетной книжки", "Номер приоритета", "Активность")
            participationIndexExcel++

            for (p in projectParticipations.sortedBy { it.priority }) {
                var student = students.find { it.id == p.studentId }!!
//                if (student == null) {
//                    student = Student(
//                        id = p.studentId,
//                        name = p.studentName,
//                        realGroup = p.group
//                    )
//                }

                workSheet.getRange("A$participationIndexExcel:F$participationIndexExcel").value = arrayOf(
                    student.name,
                    student.fullGroupName,
                    student.id,
                    p.priority,
                    if (p.priority == 5) "Молчун" else if (p.priority == 4) "Не попал на свои проекты по заявкам" else "Активный"
                )
                participationIndexExcel++
            }
            index++
        }
        workBook.save(finalPath)
        deleteExcessLists(finalPath, filePath, index, institute.name, isUniformly)
    }

    private fun deleteExcessLists(filePath: String, newFilePath: String, lastSheetNumber: Int, institute: String, isUniformly: Boolean) {

        val inputStream = FileInputStream(filePath)
        val book = WorkbookFactory.create(inputStream)

        book.removeSheetAt(lastSheetNumber)
        val f = if (isUniformly) File("$newFilePath/output/равномерно_$institute.xlsx")
        else File("$newFilePath/output/$institute.xlsx")
        f.createNewFile()
        val outputStream = FileOutputStream(f)
        book.write(outputStream)
        outputStream.close()
    }
}
