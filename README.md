# spring-cloud-architecture
## 클라우드_아키텍처 설계 &amp; 배포

### 개요

AWS 클라우드에서 배포와 운영하는 것을 단계별로 구현하였습니다.

핵심 구현 사항
* 예산 설정
* 네트워크 구축 및 핵심 기능 배포
* DB 분리 및 보안
* 프로필 사진 기능 추가

---

### 주요 구현 기능

#### [LV 0] 비용 관리 (AWS Budget)
목적: 예기치 못한 요금 발생 방지 ($100 예산, 80% 도달 시 알림)

<img width="2088" height="1024" alt="image" src="https://github.com/user-attachments/assets/3228bfbf-7e86-4361-9943-016536585b9e" />
<img width="2080" height="1133" alt="image" src="https://github.com/user-attachments/assets/24eb42be-d9ee-45c2-b782-5b0fa01cf408" />

---

#### [LV 1] 네트워크 및 핵심 기능 구축
인프라: VPC 내 Public/Private Subnet 분리 및 EC2 배포

주요 API:
* POST /api/members: 팀원 정보(이름, 나이, MBTI) 저장
* GET /api/members/{id}: 팀원 정보 조회

운영 전략:
* local/prod 프로필 분리 (H2 / MySQL)

<img width="1187" height="1057" alt="image" src="https://github.com/user-attachments/assets/c921b431-c24a-4110-a5e7-1b86ddb11ab7" />
<img width="1005" height="846" alt="image" src="https://github.com/user-attachments/assets/bd1372c6-3860-468a-a367-1c14ae44e4f5" />
<img width="1004" height="781" alt="image" src="https://github.com/user-attachments/assets/b1d606b0-c162-4a34-8a51-fc4edae17d45" />
<img width="1244" height="600" alt="image" src="https://github.com/user-attachments/assets/df48faeb-f463-4e05-bb87-dafd192d39e0" />
<img width="889" height="51" alt="image" src="https://github.com/user-attachments/assets/5841086a-0617-4f70-9768-33e5c290d70e" />
<img width="887" height="146" alt="image" src="https://github.com/user-attachments/assets/45ea0bf2-11ff-4bac-92c7-8f1fdfed6525" />


---

#### [LV 2] DB 분리 및 보안 강화

인프라: MySQL RDS 구축 및 EC2 전용 보안 그룹 체이닝 적용

보안: Parameter Store(SSM)를 통한 DB 접속 정보 주입

Actuator 확장: /actuator/info를 통해 team-name 노출
* http://43.200.184.67:8080/actuator/info

<img width="2121" height="1251" alt="image" src="https://github.com/user-attachments/assets/f8f5a454-807e-4d56-9a2f-97a70a31339f" />
<img width="1592" height="952" alt="image" src="https://github.com/user-attachments/assets/a45ed46b-b62f-4601-9971-d3cfaf8d5b4b" />



---

#### [LV 3] 프로필 사진 기능 및 권한 관리 (S3)

인프라: S3 버킷 생성 (퍼블릭 액세스 차단)

권한 관리: IAM Role을 EC2에 연결하여 Access Key 없이 S3 접근 권한 확보

핵심 API:

* POST /api/members/{id}/profile-image: S3 업로드 및 DB URL 업데이트

* GET /api/members/{id}/profile-image: 7일 유효기간의 Presigned URL 반환

Date=20260312T232803
만료 시간 : 3월 19일 22시 39분

<img width="1012" height="985" alt="image" src="https://github.com/user-attachments/assets/58a1fdea-5295-44ea-80f3-f86c2152e459" />

<img width="1347" height="1523" alt="image" src="https://github.com/user-attachments/assets/c2f09132-061a-4141-bad8-44ea4fda314d" />

---

#### Tech Stack

| 분류 | 기술 스택 |
| :--- | :--- |
| **Framework** | Spring Boot |
| **Database** | H2 (Local), AWS RDS MySQL (Prod) |
| **Infra** | AWS EC2, VPC, S3, RDS, Parameter Store |
| **Security** | IAM Role (Access Key-less) |
| **Monitoring** | Spring Boot Actuator |


