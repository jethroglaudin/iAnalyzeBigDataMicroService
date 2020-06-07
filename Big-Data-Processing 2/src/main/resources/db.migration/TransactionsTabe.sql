CREATE TABLE transactions(
    id BIGSERIAL PRIMARY KEY,
    step INTEGER,
    type VARCHAR (50),
    amount FLOAT,
    name_orig VARCHAR (50),
    old_balance_orig FLOAT,
    new_balance_orig FLOAT,
    name_dest VARCHAR (50),
    old_balance_dest FLOAT,
    new_balance_dest FLOAT,
    is_fraud INTEGER,
    is_flagged_fraud INTEGER
 );