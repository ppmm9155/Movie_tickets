-- 영화 좌석 예매 프로그램 SQL 초기화 

--테이블부터 삭제
DROP TABLE ticket CASCADE CONSTRAINTS;
DROP TABLE seat_info CASCADE CONSTRAINTS;
DROP TABLE movies CASCADE CONSTRAINTS;
DROP SEQUENCE ticket_seq;
DROP SEQUENCE seat_seq;
DROP SEQUENCE movie_seq;

-- 테이블 
CREATE TABLE movies (
    movie_id NUMBER PRIMARY KEY,
    screen_id NUMBER NOT NULL,
    title VARCHAR2(36) NOT NULL,
    movie_start DATE NOT NULL,
    movie_time NUMBER NOT NULL
);

CREATE TABLE seat_info (
    seat_id NUMBER PRIMARY KEY,
    screen_id NUMBER NOT NULL,
    seat_row VARCHAR2(3) NOT NULL,
    seat_col NUMBER NOT NULL,
    UNIQUE (screen_id, seat_row, seat_col)
);

CREATE TABLE ticket (
    ticket_id NUMBER PRIMARY KEY,
    movie_id NUMBER NOT NULL REFERENCES movies(movie_id),
    seat_id NUMBER NOT NULL REFERENCES seat_info(seat_id),
    ticket_num NUMBER DEFAULT 1 NOT NULL CHECK (ticket_num = 1),
    UNIQUE (movie_id, seat_id)
);

-- 시퀀스 생성
CREATE SEQUENCE movie_seq START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 10000 NOCACHE;
CREATE SEQUENCE seat_seq START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 250 NOCACHE;
CREATE SEQUENCE ticket_seq START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 10000 NOCACHE;

-- 영화 3개 입력 (오늘 날짜 기준)
INSERT INTO movies (movie_id, screen_id, title, movie_start, movie_time) VALUES
(movie_seq.NEXTVAL, 1, '치이카와 인어섬', TRUNC(SYSDATE) + (10*60+25)/1440, 120);
INSERT INTO movies (movie_id, screen_id, title, movie_start, movie_time) VALUES
(movie_seq.NEXTVAL, 1, '스파이더맨', TRUNC(SYSDATE) + (15*60)/1440, 150);
INSERT INTO movies (movie_id, screen_id, title, movie_start, movie_time) VALUES
(movie_seq.NEXTVAL, 1, '암살자들', TRUNC(SYSDATE) + (20*60+15)/1440, 130);

-- 좌석 정보 25개 입력 
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'A', 1);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'A', 2);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'A', 3);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'A', 4);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'A', 5);

INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'B', 1);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'B', 2);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'B', 3);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'B', 4);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'B', 5);

INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'C', 1);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'C', 2);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'C', 3);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'C', 4);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'C', 5);

INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'D', 1);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'D', 2);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'D', 3);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'D', 4);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'D', 5);

INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'E', 1);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'E', 2);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'E', 3);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'E', 4);
INSERT INTO seat_info (seat_id, screen_id, seat_row, seat_col) VALUES (seat_seq.NEXTVAL, 1, 'E', 5);

-- 예매 티켓 4개 
-- C3 = seat_id 13, C5 = seat_id 15 
INSERT INTO ticket (ticket_id, movie_id, seat_id, ticket_num) VALUES (ticket_seq.NEXTVAL, 1, 13, DEFAULT);
INSERT INTO ticket (ticket_id, movie_id, seat_id, ticket_num) VALUES (ticket_seq.NEXTVAL, 1, 15, DEFAULT);
INSERT INTO ticket (ticket_id, movie_id, seat_id, ticket_num) VALUES (ticket_seq.NEXTVAL, 2, 13, DEFAULT);
INSERT INTO ticket (ticket_id, movie_id, seat_id, ticket_num) VALUES (ticket_seq.NEXTVAL, 3, 13, DEFAULT);
COMMIT;

-- 조회
SELECT * FROM movies ORDER BY movie_id;
SELECT * FROM seat_info ORDER BY seat_id;
SELECT * FROM ticket ORDER BY ticket_id;

