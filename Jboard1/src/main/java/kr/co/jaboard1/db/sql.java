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
	
	public static final String SELECT_USER_FOR_LOGIN = "SELECT * FROM `User` WHERE `uid`=? AND `pass`=SHA2(?, 256)";
	
	public static final String INSERT_ARTICLE = "INSERT INTO `Article` SET "
												+ "`title`=?, "
												+ "`content`=?, "
												+ "`writer`=?, "
												+ "`regip`=?, "
												+ "`rdate`=NOW()";
	
	public static final String SELECT_ARTICLE = "SELECT * FROM `Article` where `no`=?";
	public static final String SELECT_ARTICLES = "SELECT * FROM `Article` ORDER BY `no`";
}
