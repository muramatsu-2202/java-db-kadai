package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {

	 public static void main(String[] args) {
		 
		 Connection con = null;
	     PreparedStatement preparedStatement = null;
		 
	     //PostsData
	     String[][] postsData = {
	    		 
	    	 {"1003", "2023-02-08", "機能の夜は徹夜でした・・", "13"},
	    	 {"1002", "2023-02-08", "お疲れ様です！", "12" },
	    	 {"1003", "2023-02-09", "今日も頑張ります！", "18" },
	    	 {"1001", "2023-02-09", "無理は禁物ですよ！", "17" },
	    	 { "1002", "2023-02-10", "明日から連休ですね！", "20" }
	     };
	     
		 
		 try {
	            // データベースに接続
	            con = DriverManager.getConnection(
	                "jdbc:mysql://localhost/challenge_java",
	                "root",
	                ""
	            );
	            
	            System.out.println("データベース接続成功" + con);
	
	            
	            //追加用SQLクエリ準備
	            String insertSql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?);";
	            preparedStatement = con.prepareStatement(insertSql);
	           
	            System.out.println("レコード追加を実行します");
	            int rowCnt = 0;
	            for ( int i = 0; i < postsData.length; i++) {
	                
	                preparedStatement.setString(1, postsData[i][0]); // ユーザーID
	                preparedStatement.setString(2, postsData[i][1]); // 投稿日時
	                preparedStatement.setString(3, postsData[i][2]); // 投稿内容
	                preparedStatement.setString(4, postsData[i][3]); // いいね数
	            
	                
	                // SQLクエリ実行 
	                
	                rowCnt += preparedStatement.executeUpdate();
	                
	            } 
	            
	            System.out.println(rowCnt + "件のレコードが追加されました");
	            
	            String selectSql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = 1002;";
	            preparedStatement = con.prepareStatement(selectSql);

	            //　SQLクエリを実行（DBMSに送信）
	            ResultSet result = preparedStatement.executeQuery();

	            // SQLクエリの実行結果を抽出
	            int recordNumber = 0;
	            while (result.next()) {
	                Date postedAt = result.getDate("posted_at");
	                String postedContent = result.getString("post_content");
	                int likes = result.getInt("likes");
	                recordNumber++;
	                // 結果の表示
	                System.out.println("ユーザーIDが1002のレコードを検索しました");
	                System.out.println(recordNumber + "件目:投稿日時＝" + postedAt + "／投稿内容＝" + postedContent + "／いいね数＝" + likes);
	            }
	            
	        } catch (SQLException e) {
	            System.out.println("エラー発生：" + e.getMessage());
	        } finally {
	            // 使用したオブジェクトを解放
	            if (preparedStatement != null) {
	                try { preparedStatement.close(); } catch (SQLException ignore) {}
	            }
	            if (con != null) {
	                try { con.close(); } catch (SQLException ignore) {}
	            }
	        }
	 }
	 
}
