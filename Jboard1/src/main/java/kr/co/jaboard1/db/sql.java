package kr.co.jaboard1.db;

public class sql {
	
	public static final String SELECT_TERMS= "SELECT * FROM `terms`";

	public static final String INSERT_USER = "INSERT INTO `User` SET "
											+ "`uid`=?, "
											+ "`pass`=SHA2(?, 256), "
											+ "`name`=?, "
											+ "`nick`=?, "
											+ "`email`=?, "
											+ "`hp`=?, "
											+ "`regip`=?, "
											+ "`rdate`=NOW()";
}
