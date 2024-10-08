insert into user(first_name,
                 second_name,
                 third_name,
                 email,
                 password,
                 role,
                 age,
                 main_address)
values ('TestName',
        'TestSecondName',
        'TestLastName',
        'email@abv.bg',
        '123',
        'ADMIN',
        20,
        'Manastirski Livadi Adora 19');

insert into user(first_name,
                 second_name,
                 third_name,
                 email,
                 password,
                 role,
                 age,
                 main_address)
values ('TestName2',
        'TestSecondName2',
        'TestLastName2',
        'email2@abv.bg',
        '123',
        'ADMIN',
        20,
        'Manastirski Livadi Adora 19');

insert into user(id,
                 first_name,
                 second_name,
                 third_name,
                 email,
                 password,
                 role,
                 age,
                 main_address)
values (-988,
        'TestName',
        'TestSecondName',
        'TestLastName',
        'email3@abv.bg',
        '123',
        'USER',
        20,
        'Manastirski Livadi Adora 19');

insert into book(id,
                 title,
                 author,
                 isbn,
                 price,
                 stock,
                 stock_available)
values (-1234,
        'TestTitle',
        'TestAuthor',
        '-248483',
        20,
        20,
        15);

insert into user_books (id,
                        user_id,
                        book_id,
                        rent_date,
                        return_date,
                        isReturned,
                        comment)
values (-56565,
        -988,
        -1234,
        '2024-1-8',
        '2024-2-9',
        'O',
        'testComment');