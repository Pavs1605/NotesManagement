## Notes Management App
This is a simple Spring Boot application for Notes Management  that manages notes, texts and words in each text.

## Design considerations
* One note can have one text as of now.
* Each text gives info on count of number of words(Case insensitive)
* System should be able to give list of all notes available in the system


## TECHSTACK
* Java Spring boot
* REST API's
* Mongo DB
* Apache Tomcat Server
* Mockito Tests
* Docker Image
* Github pipeline

## Starting the application
* Start the application by running NotesManagementApplication.java class and run below end points to see data.

## Entities
# Notes
Represents a note in the system.

# Texts
Represents texts in the system

# Words
Represents count of number of words for a text

## Database entities
| Collection | Fields                                                       | Validations                                                             |
|------------|--------------------------------------------------------------|-------------------------------------------------------------------------|
| Notes      | _id, text_id, created_date, tag, title, system_creation_date | Unique constraint on (_id, title), Title & text_id are requireed fields |
| Books      | _id, text_content, word_id                                   | Unique constraint on _id, text_content is required                      |
| Texts      | _id, words_count                                             | Unique constraint on _id                                                |


## API Endpoints
Link - https://pavs1605.github.io/LibraryManagementAPI/

## Github pipeline with tests & docker image 
Workflow pipeline - https://github.com/Pavs1605/NotesManagement/actions/runs/8214199427/job/22466417453
Latest Image uploaded to - https://hub.docker.com/repository/docker/nerdyhat29/clockboxci/general

## Screenshots 
| Description     | Request                                        | Response                                         |
|-----------------|------------------------------------------------|--------------------------------------------------|
| Get all notes   | POST - http://localhost:8080/notes             | Response -                                       |
|                 | Body:  Application/JSON                        |                                                  |           
|                 | {                                              | {                                                |
|                 | "tag": "IMPORTANT",                            | "id": "65ec55f5c0cc7c3b3aa997a9",                |
|                 | "createdDate": "02/09/2024",                   | "tag": "IMPORTANT",                              |
|                 | "title": "Important note 10",                  | "createdDate": "02/09/2024",                     |
|                 | "textContent": "Important important important" | "title": "Important note 10",                    |
|                 |                                                |                                                  |
|                 | }                                              | "textUrl": "/notes/65ec55f5c0cc7c3b3aa997a9/text |
|                 |                                                | }                                                |
|                 |                                                |                                                  |
|                 |                                                |                                                  |
