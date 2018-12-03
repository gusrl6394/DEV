package com.itbank.samplesub;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledController {

	@Scheduled(fixedRate = 2000) // 수행 시작 기점, 2초 후 실행 
    public String fixedRateTest() {
		Date today = new Date();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String result = f.format(today);
		System.out.println("fixedRate: 2sec -> " + result);
        return result;
    }
    @Scheduled(fixedDelay = 7000) // 수행 종료 기점, 7초 후 실행 
    public void fixedDelayTest() {
        System.out.println("fixedDelay: 7sec -> " + new Date());
    }
    @Scheduled(cron = "0 0 13 * * *") // 매일 13시 수행
    public void cronTest() {
        System.out.println("cron: 0 0 13 * * * -> " + new Date());
    }
    /*  @Scheduled(cron = "0 0 12 * * *") // 매일 12시 실행
		@Scheduled(cron = "0 15 10 * * *") // 매일 10시 15분 실행
		@Scheduled(cron = "0 * 14 * * *") // 매일 14시에 0분~59분까지 매분 실행
		@Scheduled(cron = "0 0/5 14 * * *") // 매일 14시에 시작해서 5분 간격으로 실행
		@Scheduled(cron = "0 0/5 14,18 * * *") // 매일 14시, 18시에 시작해서 5분 간격으로 실행 
		@Scheduled(cron = "0 0-5 14 * * *") // 매일 14시에 0분, 1분, 2분, 3분, 4분, 5분에 실행
		@Scheduled(cron = "0 0 20 ? * MON-FRI") // 월~금일 20시 0분 0초에 실행
		@Scheduled(cron = "0 0/5 14 * * ?") // 아무요일, 매월, 매일 14:00부터 14:05분까지 매분 0초 실행 (6번 실행됨)
		@Scheduled(cron = "0 15 10 ? * 6L") // 매월 마지막 금요일 아무날이나 10:15:00에 실행
		@Scheduled(cron = "0 15 10 15 * ?") // 아무요일, 매월 15일 10:15:00에 실행
		@Scheduled(cron = "* /1 * * * *") // 매 1분마다 실행
		@Scheduled(cron = " * /10 * * * *") // 매 10분마다 실행 
		
		표현식 의미 - 문자열의 좌측에서 우측으로 순서별 의미가 존재합니다. 구분자는 space입니다.
		순서	필드명			사용 가능 값
		1	seconds			0~59, - * /
		2	minutes			0~59, - * /
		3	hours			0~23, - * /
		4	day of month		1~31, - * ? / L W
		5	month			1~12 or JAN-DEC, - * /
		6	day of week		1-7 or SUN-SAT, - * ? / L #
		7	years (optional)		1970~2099, - * /
		
		특수문자 의미
		기호	의미			사용 예
		*	모든 수			매초, 매분, 매시간
		?	해당 항목 사용안함		날짜, 요일 미지정
		-	기간 설정			10-12: 10시, 11시, 12시
		,	특정 시각			2,4,6: 월, 수, 금 동작
		/	반복			5/15: 5초에 시작하여 15초 간격으로 동작
		L	마지막 기간		해당월 마지막날, 해당 주 마지막(토)
		W	가장 가까운 평일		15W: 15일기준으로 가장 가까운 평일날 수행
		LW	L과 W의 조합		그 달의 마지막 평일에 동작
		#	몇 째 주와 요일 설정	6#3: 3번째 주 금요일, 4#2 2번째 주 수요일
     */
}
