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

create table member_update
as
select
    m.*, 
    systimestamp update_date
from 
    member m
where
    1 = 0; 
    
desc member_del;

select * from user_constraints where table_name = 'member_update';

select * from member;

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

select * from member;
update member set gender = 'F'  where id = 'honggd';

select * from member_update;

drop trigger trig_delete_member;

select * from member_del;
select * from member;

delete from member where id = 'qwerty';

select * from user_constraints where table_name = 'member_del';  

drop table member_del;


-- 주소록 테이블 생성
create table address (
        id number
        , member_id varchar2(20)
        , address varchar2(2000)
        , default_addr number(1) -- 기본 배송지 여부 1(기본) 0(기본아님)
        , created_at date default sysdate
        , constraints pk_address_id primary key(id)
        , constraints fk_address_member_id foreign key(member_id) references member(id) on delete cascade
        , constraints ck_address_default_addr check(default_addr in (0, 1))
    );
    
--drop sequence seq_address_id;
create sequence seq_address_id;

insert into
        address
    values(
        seq_address_id.nextval, 'honggd', '서울시 강남구 테헤란로 123', 1, default
    );
    insert into
        address
    values(
        seq_address_id.nextval, 'honggd', '서울시 서초구 방배동 7777', 0, default
    );
    insert into
        address
    values(
        seq_address_id.nextval, 'gogd', '경기도 구리시 소동 1000', 1, default
    );
    insert into
        address
    values(
        seq_address_id.nextval, 'sinsa', '경상남도 함양시 안의면 1234', 1, default
    );
    insert into
        address
    values(
        seq_address_id.nextval, 'qwerty', '서울시 종로구 풍동 123', 1, default
    );

    commit;
    
select * from address;
select * from member;
-- 조인
select * from member m left join address a on m.id = a.member_id where m.id = ?;
-- 넣기 위한 작업
select 
    m.*
    , a.id address_id
    , a.member_id
    , a.address
    , a.default_addr
    , a.created_at address_created_at
from member m left join address a on m.id = a.member_id where m.id = 'honggd' order by a.default_addr desc;

select m.* , a.id address_id , a.member_id , a.address , a.default_addr , a.created_at address_created_at from member m left join address a on m.id = a.member_id where m.id = 'honggd';

select * from address;
select * from member;