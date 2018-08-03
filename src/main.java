import com.liaozixu.dao.ArticleDao;

public class main {
    public static void main(String[] args){
        System.out.print(ArticleDao.getList(1,1).getRow().get(0));
    }
}
