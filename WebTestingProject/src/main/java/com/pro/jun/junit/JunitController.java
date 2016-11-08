package com.pro.jun.junit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pro.jun.service.HomeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class JunitController {

	@Autowired
	private HomeService homeSVC;

	// 가상 요청, 응답, 세션등을 전역변수로 선언한다.
	// @Before와 @Test는 별도의 메서드에서 작동될것이므로 전역변수로 설정하여 차후 @Test에서 준비된 요청들을 사용할 수 있도록 하기 위함.
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockHttpSession httpSession;

	// @Test가 실행되기 이전, 값들을 준비시키는 메서드로 junit의 @Before 어노테이션을 이용한다.
	@Before
	public void before() {
		httpSession = new MockHttpSession();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();

		// 가상 요청의 파라미터에 테스트 데이터를 대입한다.
		request.setParameter("gallery_id", "chickenappr");

		// 세션이 존재한다면 이 문장으로 등록하도록 하자.
		request.setSession(httpSession);

		// 다른 쓰레드에서 설정된 리퀘스트의 파라미터나 어트리뷰트등 설정된 값들을
		// 실제 구동될 서블릿에 대입하는 구간이다.
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	@Test
	public void test() {
		homeSVC.testServiceMethod();
	}
}