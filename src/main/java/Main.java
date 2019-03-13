
public class Main {
    private static DbWorker worker;
    public static void main(String[] args) {
        worker = new DbWorker();
        String sqlRequest= "INSERT INTO public.user (firstname, lastname) VALUES('bob','marley');";
        worker.workWithDataWhithoutAnswer(sqlRequest);

    }
}
