-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: quiz_db
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `quiz`
--

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz` (
  `answer` bit(1) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_no` bigint NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `content` varchar(500) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8496frywvc6u1fj9u2lhp57u5` (`member_no`),
  CONSTRAINT `FK8496frywvc6u1fj9u2lhp57u5` FOREIGN KEY (`member_no`) REFERENCES `member` (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz`
--

LOCK TABLES `quiz` WRITE;
/*!40000 ALTER TABLE `quiz` DISABLE KEYS */;
INSERT INTO `quiz` VALUES (_binary '\0',NULL,1,2,NULL,'달걀은 어린 닭이 낳은 것일수록 그 크기가 크다.\r\n\r\n'),(_binary '\0',NULL,2,2,NULL,'고대 원시인들의 가장 큰 적은 공룡이었다.\r\n\r\n'),(_binary '',NULL,3,2,NULL,'탱고의 고장은 \'아르헨티나\'다.\r\n\r\n'),(_binary '',NULL,4,2,NULL,'위나라에서 최초로 지정된 구립 공원은 지리산이다.\r\n\r\n'),(_binary '','2025-08-20 15:28:04.921486',6,2,'2025-08-20 15:28:04.921486','차례 상을 차릴 때 흰 과일은 동쪽, 붉은 과일은 서쪽에 놓는다.\r\n\r\n'),(_binary '\0','2025-08-20 15:28:15.469066',7,2,'2025-08-20 15:28:15.469066','육상 선수가 한쪽 발에만 운동화를 신고 경기할 수 없다.\r\n\r\n'),(_binary '\0',NULL,8,1,NULL,'지구에서 육안으로 한 번에 볼 수 있는 별의 숫자는 약 4,000개 정도이다.'),(_binary '',NULL,9,1,NULL,'달팽이도 이빨이 있다.'),(_binary '',NULL,10,1,NULL,'달팽이도 이빨이 있다.'),(_binary '',NULL,11,1,NULL,'지하철 1량 (칸)에는 출입문이 모두 8개이다.'),(_binary '',NULL,12,1,NULL,'세계에서 제일 처음으로 텔레비전 방송을 시작한 나라는 영국이다.'),(_binary '',NULL,13,1,NULL,'말도 잠을 잘 때는 사람과 같이 코를 곤다.'),(_binary '\0',NULL,14,1,NULL,'셰익스피어 희곡 햄릿의 주인공인 햄릿은 네덜란드 사람이다.'),(_binary '\0',NULL,15,1,NULL,'바늘 한 쌍은 모두 22개이다.'),(_binary '',NULL,16,1,NULL,'북두칠성은 시계의 반대 방향으로 회전한다.'),(_binary '',NULL,17,1,NULL,'게의 다리는 모두 10개이다.'),(_binary '',NULL,18,1,NULL,'열대 지방에 자라는 나무에는 나이테가 없다.'),(_binary '',NULL,19,1,NULL,'늑대는 개 과, 호랑이는 고양이과에 속한다. 닭은 꿩과에 속한다.'),(_binary '',NULL,20,1,NULL,'여자들은 흔히 사용하는 화장용 크림은 약간 단맛이 나는데, 그 이유는 화장용 크림 속에 글리셀린 성분이 들어있기 때문이다.'),(_binary '\0',NULL,21,1,NULL,'벼룩은 암컷과 수컷 가운데 수컷의 몸집이 더 크다.'),(_binary '',NULL,22,1,NULL,'딸기는 장미과에 속한다.'),(_binary '',NULL,23,1,NULL,'여의도 국회의사당을 둘러 ?고 있는 돌기둥의 수는 모두 24개이다.'),(_binary '\0',NULL,24,1,NULL,'아라비아 숫자 1부터 100사이에는 9라는 숫자가 모두 19개 들어 있다.'),(_binary '',NULL,25,1,NULL,'금강산은 경치가 아름다워 4계절마다 불리우는 이름이 다르다.'),(_binary '',NULL,26,1,NULL,'병아리도 배꼽이 있다.'),(_binary '',NULL,27,1,NULL,'전쟁시 여자아이보다 남자아이의 출생률이 높다.'),(_binary '',NULL,28,1,NULL,'전쟁시 여자아이보다 남자아이의 출생률이 높다.'),(_binary '',NULL,29,1,NULL,'향수 선택법 : 향수를 바르게 고르려면 손등에 바른 후 10분 경과한 뒤에 냄세를 맡아보아야 한다.'),(_binary '\0',NULL,30,1,NULL,'세계적으로 가장 많이 발생하는 병은 말라리아 (학질) 이다.'),(_binary '\0',NULL,31,1,NULL,'세계 최초의 신용카드는 아메리칸 익스프레스이다.'),(_binary '',NULL,32,1,NULL,'채찍이라는 뜻을 가진 \'람바다\'는 브라질 춤이다.'),(_binary '',NULL,33,1,NULL,'밀물과 썰물 현상은 하루 2번씩 일어난다.'),(_binary '\0',NULL,34,1,NULL,'고추 1관은 5Kg 이다.'),(_binary '\0',NULL,35,1,NULL,'조선시대 호패는 (주민등록증) 16세 이상 모든 남녀가 소지했다.'),(_binary '',NULL,36,1,NULL,'인간의 뇌 세포는 재생이 안 되는 신체세포이다.'),(_binary '',NULL,37,1,NULL,'사람의 5가지 (시각, 후각, 미각, 청각, 촉각) 충에서 가장 먼저 나빠지는 감각기관은 시각이다.'),(_binary '\0',NULL,38,1,NULL,'우리나라 최초의 대중가요는 1923년부터 불리워진 \'희망가\'이다.'),(_binary '',NULL,39,1,NULL,'우리나라에서 가장 넓은 차선은 광화문 앞에 16차선이다.'),(_binary '',NULL,40,1,NULL,'시내버스 경로석은 6석 이상이 되어야 한다.'),(_binary '\0',NULL,41,1,NULL,'용은 십장생의 하나다.'),(_binary '',NULL,42,1,NULL,'일간신문은 3종 우편물이다.'),(_binary '',NULL,43,1,NULL,'물고기도 기침을 한다.'),(_binary '',NULL,44,1,NULL,'사슴뿔은 매년 빠졌다 다시 난다.'),(_binary '',NULL,45,1,NULL,'사람의 땀은 산성이다.'),(_binary '',NULL,46,1,NULL,'소는 꿇어 낮을 때 앞 다리부터 앉는다. 그럼 말은 뒷다리부터 꿇어앉는다.'),(_binary '',NULL,47,1,NULL,'색소폰은 그 이름이 최초의 연주자 이름으로부터 유래된 것이다.'),(_binary '',NULL,48,1,NULL,'로댕의 생각하는 사람은 오른손으로 턱을 받치고 있다.'),(_binary '',NULL,49,1,NULL,'남자와 여자의 목소리 중 멀리 들리는 것은 여자 목소리다.'),(_binary '\0',NULL,50,1,NULL,'열대어가 입을 맞추는 것은 애정의 표현이다.'),(_binary '\0',NULL,51,1,NULL,'축구 경기에서 사용되는 공은 흰색과 검은색으로 만들어야 한다.'),(_binary '\0',NULL,52,1,NULL,'마라톤은 42.195 Km를 달린다. 이 거리는 제1회 아테네 올림픽부터 채택된 것이다.'),(_binary '\0',NULL,53,1,NULL,'단오 날 여인들은 \'청포\' KFQ은 물로 머리를 감았다.'),(_binary '\0',NULL,54,1,NULL,'고래는 쉼 5M 이하의 물 속에서 잠을 잔다.'),(_binary '\0',NULL,55,1,NULL,'역사상 한 여인이 가장 많은 아이를 낳은 출산기록이 60명 이하다.'),(_binary '',NULL,56,1,NULL,'원숭이에게도 지문이 있다. 맞을까, 틀릴까?'),(_binary '\0',NULL,57,1,NULL,'호적법에 의하면 사람이 출생한 후 출생신고를 하게 되어 있다. 신고는 보름 이내에 해야한다.'),(_binary '',NULL,58,1,NULL,'셰익스피어의 4대 비극의 하나인 \'리어왕\'의 주인공은 3명의 딸이 있다.'),(_binary '\0',NULL,59,1,NULL,'윤선도의 \'오우가\'중 다섯 벗이란 물, 돌, 소나무, 대나무, 해이다.'),(_binary '\0',NULL,60,1,NULL,'서울로 들어오는 중부고속도로 톨게이트의 입구에 파인 홈은 모두 14개이다.'),(_binary '',NULL,61,1,NULL,'코끼리의 젖꼭지는 2개이고, 고양이는 8개이고, 사람과 고래의 젖꼭지는 2개이다. 그러면 곰의 젖꼭지는 4개이다.'),(_binary '',NULL,62,1,NULL,'덴마아크의 3대 명물로는 나막신, 튜울립 그리고 풍차이다.'),(_binary '\0',NULL,63,1,NULL,'파란 전등 밑에서 빨강 종이를 보면 보라색으로 보인다.'),(_binary '\0',NULL,64,1,NULL,'기린의 목뼈는 모두 7개이다. 그러면 사람은 그보다 작은 5개이다.'),(_binary '',NULL,65,1,NULL,'서울 지하철 및 전철 호선을 갈아타는 곳은 20군데이다.'),(_binary '\0',NULL,66,1,NULL,'지상에 동물중 가장 키가 큰 동물인 기린의 울음소리는 말의 울음소리와 비슷하다.'),(_binary '\0',NULL,67,1,NULL,'빵은 순수한 우리말이다.'),(_binary '',NULL,68,1,NULL,'옛 서당에도 오늘날의 반장이 있었다.'),(_binary '',NULL,69,1,NULL,'차례 상을 차릴 때 흰 과일은 동쪽, 붉은 과일은 서쪽에 놓는다.'),(_binary '',NULL,70,1,NULL,'북쪽을 가리키는 별은 북극성, 남쪽을 가리키는 별은 남십자성이다.'),(_binary '\0',NULL,71,1,NULL,'육상 선수가 한쪽 발에만 운동화를 신고 경기할 수 없다.'),(_binary '',NULL,72,1,NULL,'난중일기는 이순신 장군이 전사하기 한달 전까지 기록되어있다.'),(_binary '\0',NULL,73,1,NULL,'비행기의 출발시간이란 비행기의 탑승문이 완전히 닫힌 순간을 말한다.'),(_binary '',NULL,74,1,NULL,'백설공주에 나오는 일곱 난쟁이의 직업은 광부였다.'),(_binary '\0',NULL,75,1,NULL,'남극에도 우편번호가 있다.'),(_binary '',NULL,76,1,NULL,'수놈 캥거루가 앞발을 들고 권투하는 모습을 보여 주는 것은 암컷에 대한 프로포즈이다.'),(_binary '',NULL,77,1,NULL,'서울 마사회 과천 경마장 정문 앞의 말 동상은 숫말이다.'),(_binary '',NULL,78,1,NULL,'대한극장 좌석 수는 1,920석이다.'),(_binary '',NULL,79,1,NULL,'버스의 무임 승차 나이는 6세 미만이다.'),(_binary '\0',NULL,80,1,NULL,'BUS라는 단어는 미국에서 처음 사용하였다.'),(_binary '',NULL,81,1,NULL,'꺼벙이란 꿩의 새끼를 말한다.'),(_binary '\0',NULL,82,1,NULL,'고래는 보통 한 마리의 새끼를 낳고 명태는 약 30만개의 알을 한번에 낳는다. 그리고 돼지는 보통 5마리 6마리의 새끼를 낳는다.'),(_binary '',NULL,83,1,NULL,'우리나라에서 김장철 김치에 쓰이는 무와 배추는 십자화과에 속한다.'),(_binary '',NULL,84,1,NULL,'영국의 위대한 극작가인 셰익스피어가 쓴 비극 \'롬오와 줄리엣\'에서 로미오가 더 오래 살았다.'),(_binary '',NULL,85,1,NULL,'가장 강한 독을 가진 개구리 1마리의 독으로 사람을 2000명 이상을 죽일 수 있다.'),(_binary '\0',NULL,86,1,NULL,'지하철 1자리에는 8명이 앉을 수 있다.'),(_binary '\0',NULL,87,1,NULL,'우리 나라 주화 중 50원 짜리에 그려진 보리 알의 개수는 35개가 넘는다.'),(_binary '\0',NULL,88,1,NULL,'맥주를 많이 마시면 배가 나온다.'),(_binary '\0',NULL,89,1,NULL,'머리를 자주 감으면 머리카락이 빠진다.'),(_binary '\0',NULL,90,1,NULL,'1부터 7까지 곱한 숫자가 1부터 100까지 더한 숫자보다 높다'),(_binary '',NULL,91,1,NULL,'개구리를 먹던 살모사가 자기 혀를 깨물었을 경우 살모사는 죽는다.'),(_binary '',NULL,92,1,NULL,'닭도 왼발잡이 , 오른발잡이가 있다.'),(_binary '\0',NULL,93,1,NULL,'다섯 손톱가운데 가장 잘 자라는 손톱은 엄지손톱이다.'),(_binary '\0',NULL,94,1,NULL,'색맹도 색깔이 있는 꿈을 꿀 수가 있다'),(_binary '',NULL,95,1,NULL,'사마귀가 있는 사람과 키스를 하면 자신도 사마귀가 생긴다.'),(_binary '\0',NULL,96,1,NULL,'개발에도 땀이 난다'),(_binary '',NULL,97,1,NULL,'사람의 몸에서 가장 불결한 곳은 발가락이다'),(_binary '',NULL,98,1,NULL,'새는 뒤로도 날 수 있다.'),(_binary '\0',NULL,99,1,NULL,'한국 돌고래와 미국 돌고래는 말이 통한다.'),(_binary '\0',NULL,100,1,NULL,'사람의 세포는 개미의 세포보다 크다.'),(_binary '\0',NULL,101,1,NULL,'비행기의 블랙박스는 검은색이다.'),(_binary '\0',NULL,102,1,NULL,'여객선의 출발 시간은 뱃고동을 통해 알린다.'),(_binary '\0',NULL,103,1,NULL,'남극을 갈 때도 비자가 필요하다.'),(_binary '',NULL,104,1,NULL,'위가 없어도 사람은 살 수 있다.'),(_binary '\0',NULL,105,1,NULL,'지구에서 육안으로 한 번에 볼 수 있는 별의 숫자는 약 4,000개 정도이다.');
/*!40000 ALTER TABLE `quiz` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-20 17:51:56
