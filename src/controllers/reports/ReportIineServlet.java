package controllers.reports;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Iine_list;
import models.Report;
import utils.DBUtil;


/**
 * Servlet implementation class ReportIineServlet
 */
@WebServlet("/reports/iine")
public class ReportIineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportIineServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //EntityManagerオブジェクト
        EntityManager em = DBUtil.createEntityManager();

        //該当のIDの日報１件のみをデータベースからリクエストで取得
        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        Iine_list i = new Iine_list();
        i.setEmployee((Employee)request.getSession().getAttribute("login_employee"));
        i.setReport(r);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        i.setCreated_at(currentTime);
        i.setUpdated_at(currentTime);

        r.setUpdated_at(new Timestamp(System.currentTimeMillis()));//更新時間(いいねした時間)　上書き
        System.out.println("最新のいいね数・計算前:" + r.getIine_goukei()); //確認用 いいね数加算前

        int iine_goukei = r.getIine_goukei() + 1;//いいね数を+1する
        System.out.println("最新のいいね数・計算後:" + iine_goukei); //確認用  いいね数加算後
        r.setIine_goukei(iine_goukei); //いいね数　計算後上書き



        //データベース更新
        em.getTransaction().begin();
        em.persist(i);
        em.getTransaction().commit();
        em.close();





        request.getSession().setAttribute("flush", "いいねしました。");

        request.getSession().removeAttribute("report_id");

        response.sendRedirect(request.getContextPath() + "/reports/index");
    }

}
