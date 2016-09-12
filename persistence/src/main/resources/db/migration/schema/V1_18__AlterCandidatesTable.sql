ALTER TABLE candidates ADD CONSTRAINT uc_cv_file_name UNIQUE (cv_file_name) ;
ALTER TABLE candidates DROP INDEX uc_cv_file_name ;
