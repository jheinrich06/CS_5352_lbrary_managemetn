import com.hp.gagawa.java.elements.*;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.List;

public class WebServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        HttpContext root = server.createContext("/");
        root.setHandler(WebServer::handleRequest);

        HttpContext context = server.createContext("/users");
        context.setHandler(WebServer::handleRequestUser);


        HttpContext book = server.createContext("/books");
        book.setHandler(WebServer::handleRequestOneBook);

        HttpContext allBooks = server.createContext("/books/all");
        allBooks.setHandler(WebServer::handleRequestAllBooks);

        HttpContext bookSearch = server.createContext("/books/search");
        bookSearch.setHandler(WebServer::handleRequestBookSearch);


        server.start();
    }

    private static void handleRequestBookSearch(HttpExchange exchange) throws IOException {
        String uri =  exchange.getRequestURI().toString();

        String keyword = uri.substring(uri.lastIndexOf('/')+1);

        DataAccess dao = new MongoDataAdapter();
        //String searchUrl = "https://www.amazon.com/s?k=" + keyword;
        List<BookModel> list = dao.loadMatchingBooks(keyword);

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendText("Book list");
        head.appendChild( title );

        Body body = new Body();

        html.appendChild( body );

        H1 h1 = new H1();
        h1.appendText("Book list");
        body.appendChild( h1 );

        P para = new P();
        para.appendChild( new Text("The server time is " + LocalDateTime.now()) );
        body.appendChild(para);

        para = new P();
        para.appendChild( new Text("The server has " + list.size() + " books." ));
        body.appendChild(para);

        Table table = new Table();
        Tr row = new Tr();
        Th header = new Th(); header.appendText("BookID"); row.appendChild(header);
        header = new Th(); header.appendText("Title"); row.appendChild(header);
        header = new Th(); header.appendText("Author"); row.appendChild(header);
        header = new Th(); header.appendText("Publisher"); row.appendChild(header);
        header = new Th(); header.appendText("Price"); row.appendChild(header);
        header = new Th(); header.appendText("Description"); row.appendChild(header);
        header = new Th(); header.appendText("Uses"); row.appendChild(header);
        table.appendChild(row);

        for (BookModel book : list) {
            row = new Tr();
            Td cell = new Td(); cell.appendText(String.valueOf(book.getBookID())); row.appendChild(cell);
            cell = new Td(); cell.appendText(book.getTitle()); row.appendChild(cell);
            cell = new Td(); cell.appendText(book.getAuthor()); row.appendChild(cell);
            cell = new Td(); cell.appendText(book.getPublisher()); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(book.getPrice())); row.appendChild(cell);
            cell = new Td(); cell.appendText(book.getDescription()); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(book.getUses())); row.appendChild(cell);
            table.appendChild(row);
        }

        table.setBorder("1");

        html.appendChild(table);
        String response = html.write();

        System.out.println(response);


        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

        /*
        Document document = Jsoup.connect(searchUrl).get();

        // Find all the search result items
        Elements searchResults = document.select(".s-result-item");

         Iterate over the search result items and extract relevant information
        for (Element result : searchResults) {
            String title = result.select(".a-text-normal").text();
            String price = result.select(".a-price .a-offscreen").text();
            String rating = result.select(".a-icon-alt").text();

            System.out.println("Title: " + title);
            System.out.println("Price: " + price);
            System.out.println("Rating: " + rating);
            System.out.println("------------------------");
        }

        String response = "The total results: " + searchResults.size(); // document.html();

        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
        */
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendText("Online shopping web server");
        head.appendChild( title );

        Body body = new Body();

        H1 h1 = new H1();
        h1.appendText("Welcome to my online shopping center");

        body.appendChild(h1);

        P para = new P();

        A link = new A("/books/all", "/books/all");
        link.appendText("Books list");

        para.appendChild(link);
        body.appendChild(para);
        html.appendChild( body );


        String response = html.write();
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void handleRequestUser(HttpExchange exchange) throws IOException {
        String response = "Hi there! I am a simple web server!";
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private static void handleRequestAllBooks(HttpExchange exchange) throws IOException {

        DataAccess dao = new MongoDataAdapter();

        List<BookModel> list = dao.loadAllBooks();

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendText("Book list");
        head.appendChild( title );

        Body body = new Body();

        html.appendChild( body );

        H1 h1 = new H1();
        h1.appendText("Book list");
        body.appendChild( h1 );

        P para = new P();
        para.appendChild( new Text("The server time is " + LocalDateTime.now()) );
        body.appendChild(para);

        para = new P();
        para.appendChild( new Text("The server has " + list.size() + " books." ));
        body.appendChild(para);

        Table table = new Table();
        Tr row = new Tr();
        Th header = new Th(); header.appendText("BookID"); row.appendChild(header);
        header = new Th(); header.appendText("Title"); row.appendChild(header);
        header = new Th(); header.appendText("Author"); row.appendChild(header);
        header = new Th(); header.appendText("Publisher"); row.appendChild(header);
        header = new Th(); header.appendText("Price"); row.appendChild(header);
        header = new Th(); header.appendText("Description"); row.appendChild(header);
        header = new Th(); header.appendText("Uses"); row.appendChild(header);
        table.appendChild(row);

        for (BookModel book : list) {
            row = new Tr();
            Td cell = new Td(); cell.appendText(String.valueOf(book.getBookID())); row.appendChild(cell);
            cell = new Td(); cell.appendText(book.getTitle()); row.appendChild(cell);
            cell = new Td(); cell.appendText(book.getAuthor()); row.appendChild(cell);
            cell = new Td(); cell.appendText(book.getPublisher()); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(book.getPrice())); row.appendChild(cell);
            cell = new Td(); cell.appendText(book.getDescription()); row.appendChild(cell);
            cell = new Td(); cell.appendText(String.valueOf(book.getUses())); row.appendChild(cell);
            table.appendChild(row);
        }

        table.setBorder("1");

        html.appendChild(table);
        String response = html.write();

        System.out.println(response);


        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    private static void handleRequestOneBook(HttpExchange exchange) throws IOException {
        String uri =  exchange.getRequestURI().toString();

        int id = Integer.parseInt(uri.substring(uri.lastIndexOf('/')+1));

        System.out.println(id);

        DataAccess dao = new MongoDataAdapter();

        Html html = new Html();
        Head head = new Head();

        html.appendChild( head );

        Title title = new Title();
        title.appendChild( new Text("Display One Book") );
        head.appendChild( title );

        Body body = new Body();

        html.appendChild( body );

        H1 h1 = new H1();
        h1.appendChild( new Text("Display One Book") );
        body.appendChild( h1 );

        P para = new P();
        para.appendChild( new Text("The server time is " + LocalDateTime.now()) );
        body.appendChild(para);

        BookModel book = dao.loadBook(id);

        if (book != null) {

            para = new P();
            para.appendText("BookID:" + book.getBookID());
            html.appendChild(para);
            para = new P();
            para.appendText("Title:" + book.getTitle());
            html.appendChild(para);
            para = new P();
            para.appendText("Author:" + book.getAuthor());
            html.appendChild(para);
            para = new P();
            para.appendText("Publisher:" + book.getPublisher());
            html.appendChild(para);
            para = new P();
            para.appendText("Price:" + book.getPrice());
            html.appendChild(para);
            para = new P();
            para.appendText("Description:" + book.getDescription());
            html.appendChild(para);
            para = new P();
            para.appendText("Uses:" + book.getUses());
            html.appendChild(para);
        }
        else {
            para = new P();
            para.appendText("Book not found");
            html.appendChild(para);
        }


        String response = html.write();

        // System.out.println(response);

        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


}
