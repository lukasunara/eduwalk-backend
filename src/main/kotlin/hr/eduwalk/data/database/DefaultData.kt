package hr.eduwalk.data.database

import hr.eduwalk.data.model.Location
import hr.eduwalk.data.model.Question
import hr.eduwalk.data.model.Walk

object DefaultData {

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
        Walk(
            id = "testtest",
            title = "Test Walk",
            description = "Testing the app dunctionalities",
            creatorId = "testTeacher",
        ),
    )

    val defaultLocations = listOf(
        /* --- Zadar - Poluotok --- */
        Location(
            id = Int.MAX_VALUE,
            latitude = 44.1172458,
            longitude = 15.2198869,
            title = "Morske orgulje",
            description = "Instrument kakav još niste vidjeli",
            imageBase64 = null,
            walkId = "default1",
        ),
        Location(
            id = Int.MAX_VALUE - 1,
            latitude = 44.1175910,
            longitude = 15.2198283,
            title = "Pozdrav Suncu",
            description = "Oprostite se od Sunca sa stilom",
            imageBase64 = null,
            thresholdDistance = 40,
            walkId = "default1",
        ),
        Location(
            id = Int.MAX_VALUE - 2,
            latitude = 44.1158372,
            longitude = 15.2244869,
            title = "Crkva sv. Donata",
            description = "Crkva iz 8. stoljeća",
            imageBase64 = null,
            thresholdDistance = 50,
            walkId = "default1",
        ),
        /* --- Test Walk --- */
        Location(
            id = Int.MAX_VALUE - 51,
            latitude = 44.1385254,
            longitude = 15.2204914,
            title = "Test Location 1",
            description = "Test description 1",
            imageBase64 = null,
            thresholdDistance = 10,
            walkId = "testtest",
        ),
        Location(
            id = Int.MAX_VALUE - 52,
            latitude = 44.1385762,
            longitude = 15.2209189,
            title = "Test Location 2",
            description = "Test description 2",
            imageBase64 = null,
            thresholdDistance = 30,
            walkId = "testtest",
        ),
        Location(
            id = Int.MAX_VALUE - 53,
            latitude = 44.1384429,
            longitude = 15.2203862,
            title = "Test Location 3",
            description = "Test description 3",
            imageBase64 = null,
            thresholdDistance = 7,
            walkId = "testtest",
        ),
    )

    val defaultQuestions = listOf(
        /* --- Test Walk --- */
        Question(
            id = Int.MAX_VALUE - 1,
            questionText = "Test Question 1?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
                "Test answer 4",
                "Test answer 5",
            ),
            correctAnswer = "Test answer 3",
            locationId = Int.MAX_VALUE - 51,
        ),
        Question(
            id = Int.MAX_VALUE - 2,
            questionText = "Test Question 2?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
            ),
            correctAnswer = "Test answer 1",
            locationId = Int.MAX_VALUE - 51,
        ),
        Question(
            id = Int.MAX_VALUE - 3,
            questionText = "Test Question 3?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
                "Test answer 4",
            ),
            correctAnswer = "Test answer 4",
            locationId = Int.MAX_VALUE - 51,
        ),
        Question(
            id = Int.MAX_VALUE - 4,
            questionText = "Test Question 4?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
            ),
            correctAnswer = "Test answer 2",
            locationId = Int.MAX_VALUE - 51,
        ),
        Question(
            id = Int.MAX_VALUE - 5,
            questionText = "Test Question 5?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
                "Test answer 4",
            ),
            correctAnswer = "Test answer 1",
            locationId = Int.MAX_VALUE - 51,
        ),
        Question(
            id = Int.MAX_VALUE - 6,
            questionText = "Test Question 6?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
            ),
            correctAnswer = "Test answer 1",
            locationId = Int.MAX_VALUE - 52,
        ),
        Question(
            id = Int.MAX_VALUE - 7,
            questionText = "Test Question 7?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
            ),
            correctAnswer = "Test answer 2",
            locationId = Int.MAX_VALUE - 52,
        ),
        Question(
            id = Int.MAX_VALUE - 8,
            questionText = "Test Question 8?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
            ),
            correctAnswer = "Test answer 3",
            locationId = Int.MAX_VALUE - 52,
        ),
        Question(
            id = Int.MAX_VALUE - 9,
            questionText = "Test Question 9?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
            ),
            correctAnswer = "Test answer 1",
            locationId = Int.MAX_VALUE - 53,
        ),
        Question(
            id = Int.MAX_VALUE - 10,
            questionText = "Test Question 10?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
            ),
            correctAnswer = "Test answer 2",
            locationId = Int.MAX_VALUE - 53,
        ),
        Question(
            id = Int.MAX_VALUE - 11,
            questionText = "Test Question 11?",
            answers = listOf(
                "Test answer 1",
                "Test answer 2",
                "Test answer 3",
            ),
            correctAnswer = "Test answer 3",
            locationId = Int.MAX_VALUE - 53,
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