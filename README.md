# 하루 일기 (Haru Diary)

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Hilt](https://img.shields.io/badge/Hilt-5E9CA0?style=for-the-badge&logo=dagger&logoColor=white)
<br>
![Kotlin](https://img.shields.io/badge/Kotlin-7963D2?style=for-the-badge&logo=kotlin&logoColor=white)
![Coroutines](https://img.shields.io/badge/Coroutines-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Flow](https://img.shields.io/badge/Kotlin%20Flow-5C6BC0?style=for-the-badge&logo=kotlin&logoColor=white)
<br>
![Navigation](https://img.shields.io/badge/Navigation-00ACC1?style=for-the-badge&logo=android&logoColor=white)
![Room](https://img.shields.io/badge/Room-BD534F?style=for-the-badge&logo=sqlite&logoColor=white)
![DataStore](https://img.shields.io/badge/DataStore-546E7A?style=for-the-badge&logo=android&logoColor=white)
<br><br>

**하루의 조각들을 모아, 온전한 당신의 이야기를 만듭니다.**

하루 일기는 매일의 생각, 감정, 경험을 손쉽게 기록하고 돌아볼 수 있도록 설계된 미니멀한 디자인의 안드로이드 일기장 애플리케이션입니다.

## 🏛️ 아키텍처 및 기술 스택

이 프로젝트는 유지보수성과 확장성을 고려하여 **MVVM (Model-View-ViewModel) 패턴** 기반의 **멀티 모듈 아키텍처**로 설계되었습니다.

### ✅ **선택 이유**
- **Multi-module Architecture**
<br>모듈 간의 의존성을 명확히 하고, 기능별 독립적인 개발 및 테스트를 가능하게 하여 코드의 재사용성과 빌드 속도 향상을 목표로 합니다.
<br>
- **MVVM (Model-View-Intent)**
<br>Presentation 레이어의 관심사를 분리하고, **UI 로직(View)**과 **비즈니스 상태(ViewModel)**를 명확하게 구분합니다.  
ViewModel은 UI 상태를 `StateFlow`로 관리하여, View는 오직 관찰(Observe)만 하며 반응형으로 구성됩니다.
<br>
- **Jetpack Compose**
<br>선언형 UI를 통해 더 적은 코드로 직관적이고 아름다운 UI를 구축하고, 생산성을 극대화하기 위해 사용합니다.

### 🛠️ **주요 기술 스택**
- **언어**: Kotlin
- **UI**: Jetpack Compose
- **비동기 처리**: Coroutines & Flow
- **의존성 주입**: Hilt
- **로컬 데이터베이스**: Room
- **백그라운드 작업**: WorkManager
      
<br>

## 📁 프로젝트 구조

```
.
├── app (최종 애플리케이션 모듈)
│
├── build-logic (Gradle Convention Plugin을 통한 빌드 로직 관리)
│
├── core (여러 모듈에서 재사용되는 공통 기능)
│   ├── designsystem (Color, Typography, Component 등 UI 디자인 시스템)
│   ├── model (앱 전반에서 사용되는 데이터 모델)
│   ├── navigation (Compose Navigation을 사용한 화면 이동 관리)
│   ├── resource (String, Drawable 등 공통 리소스)
│   ├── utils (공통 유틸리티 함수)
│   └── worker (WorkManager 관련 공통 작업)
│
├── data (데이터 레이어: Repository 구현체, 데이터 소스)
│
├── domain (도메인 레이어: UseCase, 비즈니스 로직)
│
├── feature (기능 단위 UI 및 ViewModel)
│   ├── diarylist (일기 목록)
│   ├── write (일기 작성/수정)
│   ├── home (메인 화면)
│   └── ... (기타 기능들)
│
└── local (Room DB 관련 로컬 데이터 소스)
```

<br>

## 📸 스크린샷

|                     홈                      |                     작성하기                     |                      내 정보                      |
|:------------------------------------------:|:--------------------------------------------:|:----------------------------------------------:|
| ![Screenshot_Home](URL_TO_HOME_SCREENSHOT) | ![Screenshot_Write](URL_TO_WRITE_SCREENSHOT) | ![Screenshot_MyPage](URL_TO_MYPAGE_SCREENSHOT) |

<br>

## ✨ 주요 기능 상세

### **✍️ 쉽고 빠른 일기 작성**
- 날짜를 선택하고 오늘의 기분, 날씨, 그리고 이야기를 자유롭게 기록할 수 있습니다.
- 직관적인 에디터는 오롯이 글쓰기에만 집중할 수 있는 환경을 제공합니다.
<br>
### **💖 좋아요로 모아보기**
- 특별히 기억하고 싶은 날의 일기에는 '좋아요'를 눌러보세요.
- '좋아요'한 일기만 따로 모아볼 수 있어, 소중한 기억들을 언제든 쉽게 다시 꺼내볼 수 있습니다.
<br>
### **🎨 사용자 맞춤 설정**
- **다크 모드**: 시스템 설정을 따르거나, 앱 내에서 직접 라이트/다크 모드를 선택하여 눈을 편안하게 보호하세요.
- **폰트 변경**: 제공되는 다양한 폰트 중 마음에 드는 것을 골라 나만의 감성으로 앱을 꾸밀 수 있습니다.
- **언어 설정**: 한국어와 영어를 지원하며, 추후 더 많은 언어가 추가될 예정입니다.
<br>
### **🔒 강력한 개인정보 보호**
- 앱 실행 시 비밀번호를 입력하도록 설정하여 나만의 비밀스러운 공간을 안전하게 지킬 수 있습니다.
<br>
### **🔔 잊지 않도록, 일기 알림**
- 매일 원하는 시간에 알림을 설정하여 일기 쓰는 습관을 꾸준히 이어갈 수 있도록 도와줍니다.
