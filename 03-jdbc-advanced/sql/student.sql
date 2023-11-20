-- (system계정) student계정 생성
-- student/student
alter session set "_oracle_script" = true;

create user student
identified by student
default tablespace users;

grant connect,resource to student;
alter user student quota unlimited on users;

-- member테이블 생성
create table member (
        id varchar2(20)
        , name varchar2(100) not null
        , gender char(1)
        , birthday date
        , email varchar2(500) not null
        , point number default 1000
        , created_at timestamp default systimestamp
        , constraints pk_member_id primary key(id)
        , constraints uq_member_email unique(email)
        , constraints ck_member_gender check(gender in ('M', 'F'))
);

insert into
    member
values(
    'honggd', '홍길동', 'M', '1999-09-09', 'honggd@naver.com', default, default
);
insert into
    member
values(
    'gogd', '고길동', 'M', '1980-02-15', 'gogd@naver.com', default, default
);
insert into
    member
values(
    'sinsa', '신사임당', 'F', '1995-05-05', 'sinsa@naver.com', default, default
);
insert into
    member
values(
    'leess', '이순신', null, null, 'leess@naver.com', default, default
);
insert into
    member
values(
    'qwerty', '쿼티', 'F', null, 'qwerty@naver.com', default, default
);

commit;

select * from member order by created_at desc;
select * from member where id = 'honggd';
select * from member where name like '%길동';

delete from member where id = 'heona';

update
    member
set
    name = '신현아'
where
    id = 'heona';
    
-- member_del 테이블 생성
create table member_del 
as
select
   id, name, gender, birthday, email, point, created_at 
from
    member;
-- 내용 삭제.... 속성만 복사 할 수 있음.. 더 찾아볼 것
delete from member_del;
-- 퇴사일 컬럼 추가
alter table member_del add del_date varchar2(100) not null;

-- trig_delete_member 생성
create or replace trigger trig_delete_member
    after
    delete on member 
    for each row
begin
        if deleting then 
         insert into
                    member_del
            values (
                    :old.id
                    , :old.name
                    , :old.gender
                    , :old.birthday
                    , :old.email
                    , :old.point
                    , :old.created_at
                    , sysdate);      
    end if;
end;
/

select * from member_del;
select * from member;

delete from member where id = 'heona';

select * from user_constraints where table_name = 'member_del';  