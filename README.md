## Notes Management App
This is a simple Spring Boot application for Notes Management  that manages notes, texts and words in each text.

## Design considerations
* One note can have one text as of now.
* Each text gives info on count of number of words(Case insensitive)
* System should be able to give list of all notes available in the system


## Techstack
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
### Notes
Represents a note in the system.

### Texts
Represents texts associated with word

### Words
Represents count of number of words for a text

## Database entities
| Collection | Fields                                                                                  | Validations                                                            |
|------------|-----------------------------------------------------------------------------------------|------------------------------------------------------------------------|
| Notes      | _id, text_id, created_date, tag, title, system_creation_date, Index on title for search | Unique constraint on (_id, title), Title & text_id are required fields |
| Texts      | _id, text_content, word_id, Index on text for search                                    | Unique constraint on _id, text_content is required                     |
| Words      | _id, words_count                                                                        | Unique constraint on _id                                               |


## API Endpoints
Link - https://pavs1605.github.io/LibraryManagementAPI/

## Github pipeline with tests & docker image 
Workflow pipeline - https://github.com/Pavs1605/NotesManagement/actions </br>
Last Workflow pipeline - https://github.com/Pavs1605/NotesManagement/actions/runs/8221930572 </br>
Latest Image uploaded to - https://hub.docker.com/repository/docker/nerdyhat29/clockboxci/general

## API Requests/Response 
### 1. Get all notes
URL : http://localhost:8080/notes  <br />
HTTP Request : GET <br />

<details>
<Summary> Get response </Summary>
<body>

```json

{
    "notes": [
        {
            "id": "65ebdd8fb305a6747dc9868b",
            "tag": "IMPORTANT",
            "createdDate": "02/09/2024",
            "title": "Important note 9",
            "textUrl": "/notes/65ebdd8fb305a6747dc9868b/texts"
        },
        {
            "id": "65ec55f5c0cc7c3b3aa997a9",
            "tag": "IMPORTANT",
            "createdDate": "02/09/2024",
            "title": "Important note 10",
            "textUrl": "/notes/65ec55f5c0cc7c3b3aa997a9/texts"
        },
        {
            "id": "65eaba75fe1c3d551d788c15",
            "tag": "PERSONAL",
            "createdDate": "02/08/2024",
            "title": "Personal note1",
            "textUrl": "/notes/65eaba75fe1c3d551d788c15/texts"
        },
        {
            "id": "65eb17c434284f16bfc9df69",
            "tag": "PERSONAL",
            "createdDate": "02/09/2024",
            "title": "Personal note 8",
            "textUrl": "/notes/65eb17c434284f16bfc9df69/texts"
        },
        {
            "id": "65eb188034284f16bfc9df6c",
            "tag": "PERSONAL",
            "createdDate": "02/09/2024",
            "title": "Personal note 8",
            "textUrl": "/notes/65eb188034284f16bfc9df6c/texts"
        },
        {
            "id": "65eb189334284f16bfc9df6f",
            "tag": "BUSINESS",
            "createdDate": "02/09/2024",
            "title": "Personal note 9",
            "textUrl": "/notes/65eb189334284f16bfc9df6f/texts"
        },
        {
            "id": "65eb1a05d046f5373a447e0a",
            "tag": "IMPORTANT",
            "createdDate": "02/09/2024",
            "title": "Personal note 9",
            "textUrl": "/notes/65eb1a05d046f5373a447e0a/texts"
        },
        {
            "id": "65eb1bc456d5da7e3bafe9da",
            "tag": "IMPORTANT",
            "createdDate": "02/09/2024",
            "title": "Personal note 9",
            "textUrl": "/notes/65eb1bc456d5da7e3bafe9da/texts"
        },
        {
            "id": "65e983797231d5d9b3248dd2",
            "tag": "PERSONAL",
            "createdDate": "02/06/2024",
            "title": "Personal note1",
            "textUrl": "/notes/65e983797231d5d9b3248dd2/texts"
        }
    ],
    "remainingElements": 9,
    "totalPages": 1,
    "currentPage": 0,
    "currentSize": 10,
    "totalElements": 9
}
```
</body>
</details>

### 2. Post/Create a note

URL : http://localhost:8080/notes  <br />
HTTP Request : POST <br />
Content-type : Application/JSON 

<details>
<Summary> Request payload  </Summary>
<body>

```json
{
  "tag": "IMPORTANT",
  "createdDate": "02/10/2024",
  "title": "Important note 1",
  "textContent": "this is a very very Important note!!!"
}
```
</body>
</details>

<details>
<Summary> Post Response </Summary>
<body>

```json
{
  "id": "65ed80f6161cd30346f6d6ad",
  "tag": "IMPORTANT",
  "createdDate": "02/10/2024",
  "title": "Important note 1",
  "textUrl": "/notes/65ed80f6161cd30346f6d6ad/texts"
}
```
</body>
</details>

<details>
<Summary> Texts object Response </Summary>
<body>
URL - http://localhost:8080/notes/65ed80f6161cd30346f6d6ad/texts

```json
{
  "id": "65ed80f6161cd30346f6d6ac",
  "textContent": "this is a very very Important note!!!",
  "words": {
    "id": "65ed80f6161cd30346f6d6ab",
    "text": null,
    "wordsCount": {
      "very": 2,
      "a": 1,
      "note": 1,
      "Important": 1,
      "this": 1,
      "is": 1
    }
  },
  "wordsUrl": "/texts/65ed80f6161cd30346f6d6ac/words"
}
```
</body>
</details>

### 3. Update text in a note
URL : http://localhost:8080/notes/{noteId}/texts/{textId} </br>
HTTP Request: GET </br>
Content-type : Application/JSON </br>

<details>
<Summary> Request payload  </Summary>
<body>

```json
{
  "textContent" : "This is a important important note note note"
}
```
</body>
</details>

<details>
<Summary> Response payload  </Summary>
<body>

```json
{
  "id": "65ed9144e89ad115623c479d",
  "textContent": "This is a important important note note note",
  "words": {
    "id": "65ed80f6161cd30346f6d6ab",
    "text": null,
    "wordsCount": {
      "note": 3,
      "important": 2,
      "a": 1,
      "This": 1,
      "is": 1
    }
  },
  "wordsUrl": "/texts/65ed9144e89ad115623c479d/words"
}
```
</body>
</details>

### 4. Delete a note
URL : http://localhost:8080/notes/{noteId}/ </br>
HTTP Request: DELETE </br>

<details>
<Summary> Response payload  </Summary>
<body>
![image](https://github.com/Pavs1605/NotesManagement/blob/main/img.png)
</br>
![image](https://github.com/Pavs1605/NotesManagement/blob/main/img_1.png)
</body>
</details>

### 5. Search notes by tags or title (case-insensitive)
URL : http://localhost:8080/notes/search?searchTerm=Important </br>
HTTP Request: GET </br>


<details>
<Summary> Response payload  </Summary>
<body>

```json
[
  {
    "id": "65eb17c434284f16bfc9df69",
    "tag": "PERSONAL",
    "createdDate": "02/09/2024",
    "title": "Important note 8",
    "textUrl": "/notes/65eb17c434284f16bfc9df69/texts"
  },
  {
    "id": "65eb1a05d046f5373a447e0a",
    "tag": "IMPORTANT",
    "createdDate": "02/09/2024",
    "title": "Personal note 9",
    "textUrl": "/notes/65eb1a05d046f5373a447e0a/texts"
  }
]
```
</body>
</details>

### 6. Few more get API's
Get text for a note - http://localhost:8080/notes/{noteId}/texts/{textId} </br>
Get words for a note  - http://localhost:8080/notes/{noteId}/texts/{textId}/words/{wordId} </br>
Get all texts - http://localhost:8080/texts </br>
Get all words - http://localhost:8080/words </br>

### 7. Few more search API's 
Search by tag (case-sensitive)    - http://localhost:8080/notes/search/tag=IMPORTANT </br>
Search by title(case-insensitive) - http://localhost:8080/notes/search/title=Note </br>

## Mongo DB Screenshots
* Notes collection </br>
<details>
<Summary> Screenshot </Summary>
<body>
![image](https://github.com/Pavs1605/NotesManagement/assets/18229871/45ea1d3e-52d6-4d36-8354-b599e16bdd4a)
</body>
</details>

* Text Collection </br>
<details>
<Summary> Screenshot </Summary>
<body>
![image](https://github.com/Pavs1605/NotesManagement/assets/18229871/ec062f9c-7801-478d-aad3-aa3adba937ce)
</body>
</details>

* Words Collection </br>
<details>
<Summary> Screenshot </Summary>
<body>
![image](https://github.com/Pavs1605/NotesManagement/assets/18229871/15dd443b-4d26-49ea-a229-b79e3a3db372)
</body>
</details>

* Indexes/Validations on notes & text collection
 <details>
<Summary> Screenshot </Summary>
<body>
![image](https://github.com/Pavs1605/NotesManagement/assets/18229871/f2dbe763-7aaa-4362-bfc8-6942213bc2a4) </br>
</body>
</details>

<details>
<Summary> Screenshot </Summary>
<body>
![image](https://github.com/Pavs1605/NotesManagement/assets/18229871/cae9fc2e-a2a1-44f7-9901-9ec413cf319b)  </br>
</body>
</details>

<details>
<Summary> Screenshot </Summary>
<body>
![image](https://github.com/Pavs1605/NotesManagement/assets/18229871/3fa204fc-f8f5-4125-8a46-300bc31922e1)  </br>
</body>
</details>


* Aggregation stages
  <details>
<Summary> Screenshot </Summary>
<body>
 ![image](https://github.com/Pavs1605/NotesManagement/assets/18229871/192306d1-5314-494c-b919-1e990852c6cc)  </br>
 </body>
</details>

<details>
<Summary> Screenshot </Summary>
<body>
 ![image](https://github.com/Pavs1605/NotesManagement/assets/18229871/9149ce99-523f-4020-bdf3-c01a775fba8a)
 </body>
</details>


## Pending Items
1. Swagger API integration similar to - https://pavs1605.github.io/LibraryManagementAPI/
2. Add more unit tests, logging, comments in the code
3. Can have checksum code for texts object for regeneration of words count everytime there is an update on text
4. Currently, each note has  a single text, this can be extended to have multiple texts for a single note
5. Pagination and sorting for search API's
