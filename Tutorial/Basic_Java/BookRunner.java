public class BookRunner {
    public static void main (String[] args) {
        Book book1 = new Book();
        book1.setTitle("The Art of Computer Programming");
        Book book2 = new Book();
        book2.setTitle("Effective Java");
        Book book3 = new Book();
        book3.setTitle("Clean Code");

        System.out.println(book1.getTitle());
        System.out.println(book2.getTitle());
        System.out.println(book3.getTitle());

    }
}
