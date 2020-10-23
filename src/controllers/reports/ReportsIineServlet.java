package controllers.reports;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsIineServlet
 */
@WebServlet("/reports/iine")
public class ReportsIineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsIineServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //EntityManagerのオブジェクト
        EntityManager em = DBUtil.createEntityManager();

        //System.out.println("セッション中のreport_id : " + request.getSession().getAttribute("report_id"));
     Report r = em.find(Report.class, (Integer) (request.getSession().getAttribute("report_id")));
    //セッションスコープからレポートのIDを取得して該当のIDのレポート１件のみをデータベースから取得


    //いいねをプロパティに上書き
    r.setIine_goukei(Integer.parseInt(request.getParameter("iine_goukei")));



    //Integer iine_goukei = Integer.parseInt(request.getParameter("iine_goukei"));
    //r.setIine_goukei(iine_goukei);
    //データベース更新
    em.getTransaction().begin();

    em.getTransaction().commit();
    em.close();

    request.getSession().setAttribute("flush",  "いいねしました。");
    request.getSession().removeAttribute("report_id");
    response.sendRedirect(request.getContextPath() + "/reports/index");
}
}