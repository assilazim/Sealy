create database todo_list;

use todo_list;

create table tasks(
    id int primary key auto_increment,
    user_id bigint not null,
    user_query varchar(100) not null,
    when_added varchar(100) not null,
    is_completed varchar(5) not null
);

insert into tasks (user_id, user_query, when_added, is_completed) values (12345, "Task 1", "2020-03-01", false);
insert into tasks (user_id, user_query, when_added, is_completed) values (67890, "Task 2", "2020-03-02", false);
insert into tasks (user_id, user_query, when_added, is_completed) values (54321, "Task 3", "2020-03-03", false);