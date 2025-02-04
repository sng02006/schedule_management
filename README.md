# Schedule Management App
|Aa 기능|Method|URL|request|response|상태코드|
|-------|------|---|-------|--------|--------|
|스케줄 등록|POST|/schedule|요청 body|등록 정보|201:정상등록|
|스케줄 목록 조회|GET|/schedule|요청 param|다건 응답 정보|200:정상조회|
|스케줄 작성자별 조회|GET|/schedule/findWriter/{writer}|요청 param|다건 응답 정보|200:정상조회|
|스케줄 날짜별 조회|GET|/schedule/findDate/{date}|요청 param|다건 응답 정보|200:정상조회|
|스케줄 조회|GET|/schedule/{id}|요청 param|단건 응답 정보|200:정상조회|
|스케줄 수정|PUT|/schedule/{id}|요청 body|수정 정보|200:정상수정|
|스케줄 작성자 수정|PATCH|/schedule/writer/{id}|요청 body|수정 정보|200:정상수정|
|스케줄 할 일 수정|PATCH|/schedule/toDo/{id}|요청 body|수정 정보|200:정상수정|
|스케줄 삭제|DELETE|/schedule/{id}|요청 param|-|200:정상삭제|

![image](https://github.com/user-attachments/assets/e0b4d09c-a027-4d17-8988-e6f8d18d3084)
