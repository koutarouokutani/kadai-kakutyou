package controllers.iine_lists;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Iine_list;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class Iine_listsIndexServlet
 */
@WebServlet("/iine_lists/index")
public class Iine_listsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Iine_listsIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //EntityManegerのオブジェクトを作成
        EntityManager em = DBUtil.createEntityManager();

      //該当のIDの日報１件のみをデータベースからリクエストで取得
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));


               //いいねした人リストのページの初期設定
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }
        //日報１件のいいねしたリストのページの最大表示数などの設定
        List<Iine_list> iine_lists = em.createNamedQuery("getMyAllIine_lists", Iine_list.class)
                .setParameter("report", r)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

long iine_lists_count = (long)em.createNamedQuery("getMyIine_listsCount", Long.class)
                    .setParameter("report", r)
                    .getSingleResult();


        //EntityManeger 閉じる。
        em.close();

        //リクエスト
        request.setAttribute("report", r);
        request.setAttribute("iine_lists", iine_lists);
        request.setAttribute("iine_lists_count", iine_lists_count);
        request.setAttribute("page", page);
        System.out.println("確認:" + iine_lists);
        System.out.println("いいね数確認:" + iine_lists_count);

        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
        request.getSession().removeAttribute("flush");
        }

        //iine_listのindex.jspに飛ぶ request.setAttributeの内容を次のjspに送る。
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/iine_lists/index.jsp");
        rd.forward(request, response);
    }

}
