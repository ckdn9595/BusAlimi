<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<!-- (로그인, 회원가입 제외) 모든 페이지에 공통으로 적용될 탬플릿 -->
<title>LuxuryCity Main</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="/clc/css/cls.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript" src="/clc/js/jquery-3.5.1.min.js"></script>
<style>
html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
a:link {
  text-decoration: none;
}

a:visited {
  text-decoration: none;
}
</style>
<body class="w3-light-grey">

<!-- Top container -->
<div class="w3-bar w3-top w3-black w3-large" style="z-index:4">
  <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" onclick="w3_open();"><i class="fa fa-bars"></i>  Menu</button>
  <a href="/clc/main.clc" class="w3-bar-item w3-right"><i class="fa fa-bus fa-fw" aria-hidden="true"></i>&nbsp;LuxuryCity</a>
</div>

<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>

	<!-- 로그인 했을 경우 -->
<c:if test="${not empty MID}">
  <div class="w3-container w3-row">
    <div class="w3-col s4">
      <img src="/clc${AVT.dir}${AVT.afile}" class="w3-circle w3-margin-right" style="width:46px">
    </div>
    <div class="w3-col s8">
      <span>Welcome, <strong>${SID}</strong></span><br>
      <a href="/clc/member/logout.clc" class="w3-col m9 w3-tiny w3-round w3-button w3-orange w3-text-white" style="margin-top: 5px;">Logout</a>
    </div>
  </div>
</c:if>

	<!-- 로그인 하지 않을 경우 -->

<c:if test="${empty MID}">
  <div class="w3-container w3-row">
    <div class="w3-col w3-center">
      <span>로그인 후 이용해주세요.</span><br>
      <div class="w3-bar w3-center w3-margin-top">
	      <a href="/clc/member/login.clc" class="w3-bar-item w3-button w3-small w3-green w3-round w3-margin-right">Login</a>
	      <a href="/clc/member/join.clc" class="w3-bar-item w3-small w3-button w3-red w3-round">Join</a>
      </div>
    </div>
  </div>
</c:if>


  <hr>
<!-- 메뉴 영역 -->
  <div class="w3-container">
    <h5>Menu</h5>
  </div>
  <div class="w3-bar-block">
    <a href="/clc/main.clc" class="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black" onclick="w3_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>  Close Menu</a>
    <a href="/clc/board/board.clc" class="w3-bar-item w3-button w3-padding"><i class="fa fa-pencil fa-fw" aria-hidden="true"></i>  문의게시판</a>
    <a href="" class="w3-bar-item w3-button w3-padding"><i class="fa fa-map-marker fa-fw" aria-hidden="true"></i>  지도 검색</a>
<c:if test="${not empty MID}">
	    <a href="/clc/manager/manager.clc" class="w3-bar-item w3-button w3-padding"><i class="fa fa-user fa-fw"></i>  관리자페이지</a>
</c:if>
  </div>
</nav>

<div class="w3-main" style="margin-left:300px;margin-top:43px;">
	<div style="margin-left: 48%;">관리자 페이지</div>
	<div style="margin-top: 100px; margin-left: 35%; width:500px; height: 700px; border:1px solid black; display: inline-block; overflow: auto;" class="w3-white">
<c:forEach var="data" items="${LINE}" varStatus="st">
		<div style="border-top: 1px dotted black; border-bottom: 1px dotted black;">
			${LINE[st.index]}
		</div>
</c:forEach>
	</div>
</div>

<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:300px;margin-top:43px;">


  <div class="w3-container w3-dark-grey w3-padding-32">
    <div class="w3-row">
      <div class="w3-container w3-third">
        <h5 class="w3-bottombar w3-border-green">Region</h5>
        <p>Seoul</p>
        <p>Gyeonggi</p>
        <p>Incheon</p>
      </div>
      <div class="w3-container w3-third">
        <h5 class="w3-bottombar w3-border-red">Information Provider</h5>
        <p>TOPIS</p>
        <p>Gbis</p>
        <p>More</p>
      </div>
      <div class="w3-container w3-third">
        <h5 class="w3-bottombar w3-border-orange">Support</h5>
        <p>Increpas</p>
      </div>
    </div>
  </div>
  <!-- Footer -->
  <footer class="w3-container w3-padding-16 w3-light-grey">
    <p>Developed by <b>Silicon Valley</b></p>
  </footer>

  <!-- End page content -->
</div>


</body>
</html>