-- 테이블 생성 (schedule)
CREATE TABLE schedule
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '작성자 식별자',
    pw VARCHAR(20) COMMENT '비밀번호',
    writer VARCHAR(20) COMMENT '작성자 이름',
    toDo VARCHAR(100) COMMENT '할 일',
    writeDate DATETIME COMMENT '작성 날짜',
    modifyDate DATETIME COMMENT '수정 날짜'
);