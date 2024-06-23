package com.pace.library.dao;

//Persistence layer
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.pace.library.entity.Book;
import com.pace.library.helper.DbConnector;

public class BookDAO {
	
	//JDBC API classes for data retrieval
    private Connection connection=null;
    private PreparedStatement pstatement=null;
    private ResultSet resultSet=null;
    Book book;
    //Book book=new Book();
    ArrayList<Book> bookList=null;
    private static String bookQry="select * from book";
    
    /*******************Get books data********************/
    public ArrayList<Book> getBooks() throws ClassNotFoundException, SQLException{
    	/*Process to get Books from table into BookList:
    	  1.Get the book data from table;
    	  2.Add each book to BookList.
    	  3.Return BookList*/
    	try {
    		//open the connection
    		connection = DbConnector.createConnection();
    		//Create pstatement
    		pstatement=connection.prepareStatement(bookQry);
    		//get the result;
    		resultSet=pstatement.executeQuery();
    		bookList=new ArrayList<Book>();
    		
    		while(resultSet.next()){
    			int bookId;
    			String bookName;
    			String author;
    			float price;
    			//declare the pojo object
    			book=new Book();
    			//get the row details
    			bookId=resultSet.getInt(1);
    			bookName=resultSet.getString(2);
    			author=resultSet.getString(3);
    			price=resultSet.getFloat(4);
    			//Add this data to book bean
    			//Set the pojo with retrieved values from the row
    			book.setId(bookId);
    			book.setName(bookName);
    			book.setAuthor(author);
    			book.setPrice(price);
    			//add the book to bookList
    			bookList.add(book);
    			book=null;
    		}//end of while loop
    	}catch(SQLException sqlex) {
    		//Empty catch block
    	}finally {
    		//close connection
    		//now data is in array list object,close the connection
    		DbConnector.closeConnection();
    	}
    	return bookList;
    }
    
    /********************Show list of books*********************/
    public void showBookList(ArrayList<Book> bookList) {
    	System.out.println("In DAO Layer");
    	for(Book book:bookList) {
    		System.out.print(book.getId());
    		System.out.print("\t"+book.getName());
    		System.out.print("\t"+book.getAuthor());
    		System.out.println("\t"+book.getPrice());
    	}
    }
    
    /*********************Inserting book data************************/
    public void insertBookDetails(Book book) throws ClassNotFoundException,SQLException{
    	connection=DbConnector.createConnection();
    	
    	String insQry="insert into book values(?,?,?,?)";
    	pstatement=connection.prepareStatement(insQry);
    	pstatement.setInt(1, book.getId());
    	pstatement.setString(2, book.getName());
    	pstatement.setString(3, book.getAuthor());
    	pstatement.setFloat(4, book.getPrice());
    	
    	int rows=pstatement.executeUpdate();
    	System.out.println(rows+"Rows Inserted!");
    	DbConnector.closeConnection();
    }
    
    /***********************Deleting Book*************************/
    public boolean deleteBook(int id) throws SQLException, ClassNotFoundException{
    	pstatement=null;
    	connection=DbConnector.createConnection();
    	String delQry="delete from book where id=?";
    	pstatement=connection.prepareStatement(delQry);
    	pstatement.setInt(1, id);
    	int rows=pstatement.executeUpdate();
    	boolean isDelete=true;
    	if(rows!=0) {//if rows is non-zero,it is deleted
    		isDelete=true;//status of deletion
    	}else {
    		isDelete=false;//status of deletion
    	}
    	DbConnector.closeConnection();
    	return isDelete;
    }
    
    /********************************Updating Book*******************************/
    public boolean updateBook(int id)throws SQLException,ClassNotFoundException{//id is the argument of updateBook
    	pstatement=null;
    	connection=DbConnector.createConnection();
    	String updPriceQry="update book set price=price+price*0.10 where id=?";
    	pstatement=null;
    	pstatement=connection.prepareStatement(updPriceQry);
    	pstatement.setInt(1, id);
    	int rows=pstatement.executeUpdate();
    	boolean isUpdate=true;
    	if(rows!=0) {//if rows is available,it is Updated
    		isUpdate=true;//status of updation
    	}else {
    		isUpdate=false;//status of updation
    	}
    	DbConnector.closeConnection();
    	return isUpdate;
    	
    }
}//end of dao class
