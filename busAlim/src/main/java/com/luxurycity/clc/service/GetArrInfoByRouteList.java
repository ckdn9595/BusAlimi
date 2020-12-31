package com.luxurycity.clc.service;

import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.luxurycity.clc.dao.*;
import com.luxurycity.clc.vo.*;

//버스 도착정보 api 첫번쨰 꺼 map으로 반환해주는 클래스
public class GetArrInfoByRouteList {
	@Autowired
	SearchDao sDao;
	//xml 요청 url 만드는 함수
	public ArrayList<Integer> GetInfoByRouteList(int routeid, int district) {
		
		ArrayList<Integer> nodeinfo  = new ArrayList<Integer>();
		if(district == 1) {// 여기는 경기도 버스노선별 도착정보를 뽑는 곳입니다.
			try {
				nodeinfo = GetArrInfoByList(routeid);
				HashSet<Integer> distinctData = new HashSet<Integer>(nodeinfo);
				nodeinfo = new ArrayList<Integer>(distinctData);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else { // 여기는 서울 버스노선별 도착정보 뽑는 곳입니다.
			try {
				nodeinfo = GetSeoulArrInfoByList(routeid);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return nodeinfo;
	}
	//경기도 실시간 도착정보 데이터 반환해주는 함수
	public ArrayList<Integer> GetArrInfoByList(int routeid) {
		
		ArrayList<Integer> nodeinfo  = new ArrayList<Integer>();
		String key = "sectOrd1"; //태그 이름들 모아놈
		try {
			String url = makeURL(routeid);
			// 여긴 그냥 만들어주는 객체및 함수임 필요할때 복붙하면 됨
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			
			doc.getDocumentElement().normalize();
			System.out.println("Root Element : "+doc.getDocumentElement().getNodeName());
			
			//파싱할 태그이름 선언.. 아래 for문은 선언되는 태그 자식들을 뽑는거
			NodeList nodeList = doc.getElementsByTagName("itemList"); // 파싱할 태그 선택
			System.out.println("파싱할 리스트 수 : "+ nodeList.getLength()); // 몇번 나와야하는지 출력
			
			for(int tmp =0; tmp<nodeList.getLength(); tmp++) {
				Node nNode = nodeList.item(tmp);
				if(nNode.getNodeType()==Node.ELEMENT_NODE) {
					
					Element element = (Element) nNode;
					
					String data = (String)getTagValue(key, element);
					
					nodeinfo.add(Integer.parseInt(data));
				}
			}//for
		}catch(Exception e) {
			
		}
		return nodeinfo;
	}
	
	//서울 실시간 도착정보 데이터 반환해주는 함수
	public ArrayList<Integer> GetSeoulArrInfoByList(int routeid) {
		
		ArrayList<Integer> nodeinfo  = new ArrayList<Integer>();
		String key = "stationId"; //태그 이름들 모아놈
		try {
			String url = makeURL(routeid);
			// 여긴 그냥 만들어주는 객체및 함수임 필요할때 복붙하면 됨
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			
			doc.getDocumentElement().normalize();
			System.out.println("Root Element : "+doc.getDocumentElement().getNodeName());
			
			//파싱할 태그이름 선언.. 아래 for문은 선언되는 태그 자식들을 뽑는거
			NodeList nodeList = doc.getElementsByTagName("busLocationList"); // 파싱할 태그 선택
			System.out.println("파싱할 리스트 수 : "+ nodeList.getLength()); // 몇번 나와야하는지 출력
			
			for(int tmp =0; tmp<nodeList.getLength(); tmp++) {
				Node nNode = nodeList.item(tmp);
				if(nNode.getNodeType()==Node.ELEMENT_NODE) {
					
					Element element = (Element) nNode;
					
					String data = (String)getTagValue(key, element);
					
					nodeinfo.add(Integer.parseInt(data));
				}
			}//for
		}catch(Exception e) {
			
		}
		return nodeinfo;
	}
	

	private static String getTagValue(String tag, Element ele) {

        NodeList nodeList = ele.getElementsByTagName(tag).item(0).getChildNodes();

        Node nValue = (Node) nodeList.item(0);

        if(nValue == null) {

            return null;

        }

        return nValue.getNodeValue();

    }
	//경기 도착정보 요청 메세지
	public String makeURL(int routeid) {
		String key = "Yw3zPCyzMoX2VB0yMPfZgip2qIHGaFLGT5RuJ9gtVFGvzjbuNNZa5qB5DFUm%2BNMe%2B0kHhUWAYIH1j0BK%2Fdj6MQ%3D%3D";
		String url = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll";
		
		url += "?serviceKey=" + key + "&busRouteId=" + routeid;
		
		return url;
	}
	//서울 도착정보 요청 메세지
		public String makeSeoulURL(int routeid) {
			String key = "Yw3zPCyzMoX2VB0yMPfZgip2qIHGaFLGT5RuJ9gtVFGvzjbuNNZa5qB5DFUm%2BNMe%2B0kHhUWAYIH1j0BK%2Fdj6MQ%3D%3D";
			String url = "http://openapi.gbis.go.kr/ws/rest/buslocationservice";
			
			url += "?serviceKey=" + key + "&routeId=" + routeid;
			
			return url;
		}

}
