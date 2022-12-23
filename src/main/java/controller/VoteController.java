package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.VoteDAO;


@WebServlet("/")
public class VoteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public VoteController() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doPro(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doPro(request, response);
	}
	
	protected void doPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 프로젝트 + 파일경로까지 가져옵니다. request.getRequestURI() 
		 * 예) http://localhost:8080/project/list.jsp [return] /project/list.jsp
		 * 
		 * 전체 경로를 가져옵니다. request.getRequestURL() 
		 * 예) http://localhost:8080/project/list.jsp [return]
		 * http://localhost:8080/project/list.jsp
		 * 
		 * 파일명만 가져옵니다. request.getServletPath() 
		 * 예) http://localhost:8080/project/list.jsp
		 * [return] /list.jsp
		 * 
		 */

		String context = request.getContextPath(); //톰캣의 Context path를 가져온다(server.xml에서 확인)
		String command = request.getServletPath();
		String site = null;
		
		VoteDAO vote = new VoteDAO();
		
		switch(command) {
		 case "/main" : 
			 site = "index.jsp";
			 break;
		 case "/vote" : 
				int result = vote.insertVote(request, response);
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out=response.getWriter();
				if(result == 1) {
					out.println("<script>");
					out.println("alert('투표하기 정보가 정상적으로 등록 되었습니다!'); location.href='"+context+"'; ");
					out.println("</script>");
					out.flush();
				}else {
					out.println("<script>");
					out.println("alert('등록실패!'); location.href='"+context+"'; ");
					out.println("</script>");
					out.flush();
				}		
				break;
		 case "/memberList" : 
			 site = vote.selectMember(request, response);
				break;
		 case "/voteList" : 
			 site = vote.selectAll(request, response);
			 break;
		 case "/voteMember" :
			 site = "voteMember.jsp";
			 break;
		 case "/voteResult" : 
			 site = vote.selectResult(request, response);
			 break;
		 default : break;
		}
		
		/* 결과 */
		getServletContext().getRequestDispatcher("/"+site).forward(request, response);
	}

}
