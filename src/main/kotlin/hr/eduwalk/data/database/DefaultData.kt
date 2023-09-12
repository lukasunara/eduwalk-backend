package hr.eduwalk.data.database

import hr.eduwalk.data.model.Location
import hr.eduwalk.data.model.Question
import hr.eduwalk.data.model.User
import hr.eduwalk.data.model.UserRole
import hr.eduwalk.data.model.Walk

object DefaultData {

    val defaultUsers = listOf(
        User(username = "testTeacher", role = UserRole.TEACHER),
        User(username = "testStudent", role = UserRole.STUDENT),
    )

    val defaultWalks = listOf(
        Walk(
            id = "default1",
            title = "Zadar - Poluotok",
            description = "Nauči o ljepotama i bogatoj povijesti grada Zadra",
            creatorId = null,
        ),
        Walk(
            id = "default2",
            title = "Zagrebačke fontane",
            description = "Otkrij zanimljive činjenice o znamenitostima Trnskog naselja u Zagrebu",
            creatorId = null,
        ),
//        Walk(
//            id = "testtest",
//            title = "Test Walk",
//            description = "Testing the app functionalities",
//            creatorId = "testTeacher",
//        ),
        Walk(
            id = "testtest",
            title = "Test Walk",
            description = "Testing the app functionalities",
            creatorId = "testTeacher",
        ),
    )

    val defaultLocations = listOf(
        /* --- Zadar - Poluotok --- */
        Location(
            id = 0,
            latitude = 44.1172458,
            longitude = 15.2198869,
            title = "Morske orgulje",
            description = "Instrument kakav još niste vidjeli",
            imageBase64 = null,
            walkId = "default1",
        ),
        Location(
            id = 1,
            latitude = 44.1175910,
            longitude = 15.2198283,
            title = "Pozdrav Suncu",
            description = "Oprostite se od Sunca sa stilom",
            imageBase64 = null,
            thresholdDistance = 40,
            walkId = "default1",
        ),
        Location(
            id = 2,
            latitude = 44.1158372,
            longitude = 15.2244869,
            title = "Crkva sv. Donata",
            description = "Crkva iz 8. stoljeća",
            imageBase64 = null,
            thresholdDistance = 50,
            walkId = "default1",
        ),
        /* --- Test Walk --- */
//        Location(
//            id = 3,
//            latitude = 44.1385254,
//            longitude = 15.2204914,
//            title = "Test Location 1",
//            description = "Test description 1",
//            imageBase64 = null,
//            thresholdDistance = 10,
//            walkId = "testtest",
//        ),
//        Location(
//            id = 4,
//            latitude = 44.1385762,
//            longitude = 15.2209189,
//            title = "Test Location 2",
//            description = "Test description 2",
//            imageBase64 = null,
//            thresholdDistance = 30,
//            walkId = "testtest",
//        ),
//        Location(
//            id = 5,
//            latitude = 44.1384429,
//            longitude = 15.2203862,
//            title = "Test Location 3",
//            description = "Test description 3",
//            imageBase64 = null,
//            thresholdDistance = 7,
//            walkId = "testtest",
//        ),
//        Location(
//            id = 6,
//            latitude = 45.7857934,
//            longitude = 16.0089301,
//            title = "Test Location 1",
//            description = "Test description 1",
//            imageBase64 = null,
//            thresholdDistance = 10,
//            walkId = "testtest",
//        ),
//        Location(
//            id = 7,
//            latitude = 45.7854041,
//            longitude = 16.0092406,
//            title = "Test Location 2",
//            description = "Test description 2",
//            imageBase64 = null,
//            thresholdDistance = 30,
//            walkId = "testtest",
//        ),
        Location(
            id = 3,
            latitude = 45.8010280,
            longitude = 15.9722677,
            title = "E Zgrada",
            description = "Test description 1",
            imageBase64 = null,
            thresholdDistance = 10,
            walkId = "testtest",
        ),
        Location(
            id = 4,
            latitude = 45.8020856,
            longitude = 15.9728936,
            title = "SD Martinovka",
            description = "Test description 2",
            imageBase64 = null,
            thresholdDistance = 30,
            walkId = "testtest",
        ),
        Location(
            id = 5,
            latitude = 45.8014934,
            longitude = 15.9730563,
            title = "Park za djecu",
            description = "Test description 1",
            imageBase64 = null,
            thresholdDistance = 10,
            walkId = "testtest",
        ),
        Location(
            id = 6,
            latitude = 45.8010343,
            longitude = 15.9700116,
            title = "Caffe Bar Limb",
            description = "Test description 2",
            imageBase64 = null,
            thresholdDistance = 30,
            walkId = "testtest",
        ),
        Location(
            id = 7,
            latitude = 45.8010490,
            longitude = 15.9708820,
            title = "Skriptarnica",
            description = "Test description 1",
            imageBase64 = null,
            thresholdDistance = 10,
            walkId = "testtest",
        ),
    )

    val defaultQuestions = listOf(
        /* --- Test Walk --- */
        Question(
            id = 1,
            questionText = "Test Question 1?",
            answers = listOf(
                "Tocan odgovor",
                "Test answer 2",
                "Test answer 3",
                "Test answer 4",
                "Test answer 5",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 7,
        ),
        Question(
            id = 2,
            questionText = "Test Question 2?",
            answers = listOf(
                "Tocan odgovor",
                "Test answer 2",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 7,
        ),
        Question(
            id = 3,
            questionText = "Test Question 3?",
            answers = listOf(
                "Test answer 1",
                "Tocan odgovor",
                "Test answer 3",
                "Test answer 4",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 7,
        ),
        Question(
            id = 4,
            questionText = "Test Question 4?",
            answers = listOf(
                "Test answer 1",
                "Tocan odgovor",
                "Test answer 3",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 7,
        ),
        Question(
            id = 5,
            questionText = "Test Question 5?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
                "Tocan odgovor",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 7,
        ),
        Question(
            id = 6,
            questionText = "Test Question 1?",
            answers = listOf(
                "Tocan odgovor",
                "Test answer 2",
                "Test answer 3",
                "Test answer 4",
                "Test answer 5",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 6,
        ),
        Question(
            id = 7,
            questionText = "Test Question 2?",
            answers = listOf(
                "Tocan odgovor",
                "Test answer 2",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 6,
        ),
        Question(
            id = 8,
            questionText = "Test Question 3?",
            answers = listOf(
                "Test answer 1",
                "Tocan odgovor",
                "Test answer 3",
                "Test answer 4",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 6,
        ),
        Question(
            id = 9,
            questionText = "Test Question 4?",
            answers = listOf(
                "Test answer 1",
                "Tocan odgovor",
                "Test answer 3",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 6,
        ),
        Question(
            id = 10,
            questionText = "Test Question 5?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
                "Tocan odgovor",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 6,
        ),
        Question(
            id = 11,
            questionText = "Test Question 1?",
            answers = listOf(
                "Tocan odgovor",
                "Test answer 2",
                "Test answer 3",
                "Test answer 4",
                "Test answer 5",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 5,
        ),
        Question(
            id = 12,
            questionText = "Test Question 2?",
            answers = listOf(
                "Tocan odgovor",
                "Test answer 2",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 5,
        ),
        Question(
            id = 13,
            questionText = "Test Question 3?",
            answers = listOf(
                "Test answer 1",
                "Tocan odgovor",
                "Test answer 3",
                "Test answer 4",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 5,
        ),
        Question(
            id = 14,
            questionText = "Test Question 4?",
            answers = listOf(
                "Test answer 1",
                "Tocan odgovor",
                "Test answer 3",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 4,
        ),
        Question(
            id = 15,
            questionText = "Test Question 5?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
                "Tocan odgovor",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 4,
        ),
        Question(
            id = 16,
            questionText = "Test Question 5?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
                "Tocan odgovor",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 4,
        ),
        Question(
            id = 17,
            questionText = "Test Question 5?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
                "Tocan odgovor",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 3,
        ),
        Question(
            id = 18,
            questionText = "Test Question 5?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
                "Tocan odgovor",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 3,
        ),
        Question(
            id = 19,
            questionText = "Test Question 5?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
                "Tocan odgovor",
            ),
            correctAnswer = "Tocan odgovor",
            locationId = 3,
        ),
    )

//    val defaultWalkScores = listOf(
//        WalkScore(
//            userId = "testStudent",
//            walkId = "testtest",
//            score = 9,
//        ),
//    )
}
