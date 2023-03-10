package ru.student.distribution

import ru.student.distribution.data.model.Participation
import ru.student.distribution.data.model.Project
import ru.student.distribution.data.model.Student
import ru.student.distribution.domain.distribution.Distribution
import ru.student.distribution.domain.distribution.DistributionRule


/**
 * Example of launching algorithm
 */
private fun main() {
    val groups = mapOf("ИСТб" to "ИСТб-1", "АСУб" to "АСУб-1")
    val students = mutableListOf<Student>()
    (0..35).forEach {
        val groupName = if (it < 15) "АСУб" else "ИСТб"
        val groupNumber = groups[groupName]!!
        students.add(
            Student(id = it, name = "Name $it", groupFamily = groupName, fullGroupName = groupNumber)
        )
    }
    val projects = mutableListOf<Project>(
        Project(
            id = 1,
            title = "Project 1",
            groups = listOf("ИСТб", "АСУб"),
            places = 15,
            freePlaces = 15,
            busyPlaces = 0,
            supervisors = listOf("Supervisor 1"),
            difficulty = 1,
            customer = ""
        ),
        Project(
            id = 2,
            title = "Project 2",
            groups = listOf("АСУб"),
            places = 15,
            freePlaces = 15,
            busyPlaces = 0,
            supervisors = listOf("Supervisor 2"),
            difficulty = 1,
            customer = ""
        ),
    )
    val participation = mutableListOf<Participation>()

    participation.add(Participation(id = 0, priority = 1, projectId = 1, studentId = 15, stateId = 0))
    participation.add(Participation(id = 1, priority = 1, projectId = 1, studentId = 16, stateId = 0))
    participation.add(Participation(id = 2, priority = 2, projectId = 2, studentId = 0, stateId = 0))
    participation.add(Participation(id = 3, priority = 1, projectId = 2, studentId = 1, stateId = 0))
    participation.add(Participation(id = 4, priority = 1, projectId = 2, studentId = 2, stateId = 0))

    val institute = "Institute"
    val specialities = mutableListOf<String>("ИСТб", "АСУб")
    val specialGroups = mutableListOf<String>()

    Distribution(
        students = students,
        projects = projects,
        participations = participation,
        institute = institute,
        specialties = specialities,
        specialGroups = specialGroups,
        savedPath = "E:/yarmarka/",
        distributionRule = DistributionRule(15, 9)
    ).execute()
}

//private fun main() {
//    val groups = listOf("1", "2", "3")
//    val students = mutableListOf<Student>(
//        Student(1, "1", "1", "1",),
//        Student(2, "1", "1", "1",),
//        Student(3, "3", "3", "3",),
//        Student(4, "1", "1", "1",),
//        Student(5, "3", "3", "3",),
//        Student(6, "3", "3", "3",),
//        Student(7, "3", "3", "3",),
//        Student(8, "1", "1", "1",),
//        Student(9, "2", "2", "2",),
//    )
//    val projects = mutableListOf<Project>(
//        Project(
//            id = 1,
//            title = "Project 1",
//            groups = listOf("1", "2", "3"),
//            places = 3,
//            freePlaces = 3,
//            busyPlaces = 0,
//            supervisors = listOf("Supervisor 1"),
//            difficulty = 1,
//            customer = ""
//        ),
//        Project(
//            id = 2,
//            title = "Project 2",
//            groups = listOf("1", "2", "3"),
//            places = 3,
//            freePlaces = 3,
//            busyPlaces = 0,
//            supervisors = listOf("Supervisor 2"),
//            difficulty = 1,
//            customer = ""
//        ),
//        Project(
//            id = 3,
//            title = "Project 2",
//            groups = listOf("3"),
//            places = 3,
//            freePlaces = 3,
//            busyPlaces = 0,
//            supervisors = listOf("Supervisor 3"),
//            difficulty = 1,
//            customer = ""
//        ),
//    )
//    val participation = mutableListOf<Participation>()
//
//    participation.add(Participation(id = 0, priority = 1, projectId = 2, studentId = 1, stateId = 0))
//    participation.add(Participation(id = 1, priority = 1, projectId = 2, studentId = 3, stateId = 0))
//
//    val institute = "Institute"
//    val specialGroups = mutableListOf<String>()
//
//    Distribution(
//        students = students,
//        projects = projects,
//        participations = participation,
//        institute = institute,
//        specialties = groups,
//        specialGroups = specialGroups,
//        savedPath = "E:/yarmarka/",
//        distributionRule = DistributionRule(3, 0)
//    ).execute()
//}
