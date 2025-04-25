![image](https://github.com/user-attachments/assets/7f947ca1-88e6-44a6-9b4f-d61e3ad2af76)
## **프로젝트 소개**
<br>

### [Musinsam] MSA 기반 이커머스 플랫폼 프로젝트
본 프로젝트는 쿠팡, 11번가, 무신사와 같은 **온라인 쇼핑몰 플랫폼**을 구축하는 것을 목표로 합니다.
상품 탐색부터 주문, 결제까지의 전 과정을 온라인으로 구현한 **MSA 아키텍처 기반 전자상거래 시스템**입니다.

<br>

### **기술적 목표**
- MSA 기반 구조 설계 및 구현
- API Gateway 및 Eureka 기반 서비스 라우팅
- 공통 유틸리티 및 모듈화
- 공통 DTO 및 에러 응답 포맷 정리
- docker-compose 를 통한 공통 개발 환경 세팅
- Grafana, Prometheus 적용을 통한 모니터링 활성화
- Kafka, Redis 적용을 통한 안정성 및 성능 개선
- 데이터 감사 로그 및 Soft Delete 구현
- Swagger 기반의 API 명세 자동화
- 내부 API / 외부 API Endpoint 구분
- 정적 팩토리 메서드 패턴 적용
- Controller 계층 테스트
- API 버전 관리 체계 도입
- 보안 및 인증 로직 커스터마이징
- ZonedDateTime을 통한 타임존 처리 일관성 보장

<br>

### **구현 목표**
- 고객 경험을 극대화하기 위한 마케팅 프로모션 기능(쿠폰, 적립금, 타임 세일 등)을 유연하게 제공
- 상품과 주문 간 **재고 정합성**을 보장하는 고성능 시스템 구현
- **확장성**과 **무결성**을 갖춘 **MSA 아키텍처** 설계 및 구현

<br>

### **공통 관심사항**
- 대규모 트래픽 대응을 위한 확장성 확보
- 서비스간 독립 배포 및 확장 가능성 고려
- Spring Security + JWT 기반 인증/인가 시스템 구축
- 회원, 업체, 관리자 별 접근 가능한 리소스 구분
- 상품-주문-결제 도메인 간 정합성 보장
- 쿠폰 시스템의 유연한 적용 로직 구현
- 재고 및 주문 정합성 보장
- 마케팅 기능(쿠폰/타임세일) 유연한 설계
- AI API 연동 기능 구현 (ChatGPT)

<br>

### 팀원 역할분담

| **이름** | **역할** | **구현 기능** |
| --- | --- | --- |
| **양수영** | **팀장** | **알림, AI, 업체** |
| 박소해 | 팀원 | 상품, 이벤트 |
| 이지웅 | 팀원 | 주문, 결제 |
| 임대일 | 팀원 | 회원, 쿠폰 |

<br>

## 프로젝트 구조

<details>
<summary></summary>

```

📦musinsam
┣📂ai-service
┣📂alarm-service
┣📂coupon-service
┣📂eureka-gateway
┣📂event-service
┣📂order-service
┣📂payment-service
┣📂product-service
┣📂shop-service
┣📂user-service
┗📂common

```

</details>

### 서비스 엔드포인트

| **서비스명** | **설명** | **기본 URL** | **포트** |
| --- | --- | --- | --- |
| **Eureka** | 서비스 디스커버리 | `http://localhost:8761` | 8761 |
| **Gateway** | API Gateway | `http://localhost:10000` | 10000 |
| **User** | 사용자 서비스 | `http://localhost:10001` | 10001 |
| **Coupon** | 쿠폰 서비스 | `http://localhost:10051` | 10051 |
| **Event** | 쿠폰 서비스 | `http://localhost:11001` | 11001 |
| **Product** | 상품 서비스 | `http://localhost:11002` | 11002 |
| **Order** | 주문 서비스 | `http://localhost:12001` | 12001 |
| **Payment** | 결제 서비스 | `http://localhost:13001` | 13001 |
| **Alarm** | 알림 서비스 | `http://localhost:14001` | 14001 |
| **AI** | AI 서비스 | `http://localhost:15001` | 15001 |

<br>

### 기술 스택

| Tech | Ver | 선정 이유 |
| --- | --- | --- |
| **Java** | `17` | SpringBoot 3과의 호환, LTS 버전 선택 |
| **SpringBoot** | `3.4.4` | 최신 기능 활용, 성능 최적화, 보안 패치 적용이 용이 |
| **Spring Cloud** | `4.2.1` | 마이크로서비스 아키텍처 구현을 위한 솔루션 제공 (Netflix Eureka, OpenFeign) |
| **Postgres** | `17.4` | 대용량 데이터 처리 성능과 뛰어난 확장성을 가진 DB |
| **Redis** | `7.4.3` | 세션 관리, 캐싱, 실시간 데이터 처리를 위한 인메모리 데이터 저장소 |
| **QueryDSL** | `5.0.0` | 타입 안전성과 가독성이 높은 동적 쿼리 지원을 통해 유지보수성 향상 |
| **Kafka** | `3.3.4` | 분산 이벤트 스트리밍 플랫폼, 비동기 메시징 처리 및 시스템 간 통합에 활용 |
| **SpringDoc OpenAPI** | `2.8.6` | RESTful API 문서화를 위한 OpenAPI 스펙 자동 생성 |

<br>

### **실행 방법**

**1️⃣ docker-compose 파일 설정**

```yaml
services:
  ...

volumes:
  postgres_data:
  grafana-storage:
  prometheus_data_coupon:
  grafana_data_coupon:
  prometheus_data_shop:
  grafana_data_shop:
  prometheus_data_alarm:
  grafana_data_alarm:

networks:
  laboratory-network:
    driver: bridge
```

**2️⃣ Docker Compose 실행**

```yaml
docker-compose up -d
```

**3️⃣ API 문서 확인 (Swagger)**

gateway

```yaml
http://localhost:10000
```

<br>

### 시스템 구성도

![image (1)](https://github.com/user-attachments/assets/f3a1d720-212e-4eb3-99a9-975ba6eae4e0)

<br>

### ERD

![image (2)](https://github.com/user-attachments/assets/06dc6956-bc25-4646-9b2d-eb237dd87313)

<br>
