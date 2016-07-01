DROP PROCEDURE IF EXISTS cleanup_sp;
DELIMITER ;;
CREATE PROCEDURE cleanup_sp()
	BEGIN
        DECLARE name VARCHAR(2048);
        DECLARE drop_command VARCHAR(4096);
        DECLARE count INT;
        DECLARE CONTINUE HANDLER FOR 1217 SET @count = @count + 1;

		SET @count = 1;
		WHILE @count > 0 DO
            SELECT GROUP_CONCAT(table_name) INTO @name FROM  information_schema.tables WHERE table_schema=SCHEMA();

            IF @name IS NOT NULL THEN 
		       	SET @drop_command = CONCAT('DROP TABLE ', @name);
				PREPARE drop_statement FROM @drop_command;
				EXECUTE drop_statement;
				DEALLOCATE PREPARE drop_statement;
            END IF;

            SET @count = @count - 1;
		END WHILE;
    END;;

DELIMITER ;

CALL cleanup_sp();

DROP PROCEDURE IF EXISTS cleanup_sp;
