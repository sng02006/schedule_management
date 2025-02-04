# Schedule Management App

사용자로부터 pw, writer, toDo를 입력받아 DB에 일정을 저장하는 App입니다.

일정을 등록할 때 날짜와 시간을 생성해 작성 날짜와 수정 날짜에 저장해줍니다.

조회를 하게 되면 시간이 없는 날짜만 출력이 되게 해주고, 날짜와 시간에 따라 내림차순으로 정렬을 해줍니다.

전체 스케줄 조회와, 작성자별, 날짜별, id(스케줄의 고유 id)에 따라 조회가 가능합니다.

작성자와 할 일을 각각 필요한 것만 수정할 수 있습니다.

마지막으로, 삭제 기능을 추가하여 필요없는 스케줄의 경우 id를 통해 삭제 요청이 가능합니다.

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
