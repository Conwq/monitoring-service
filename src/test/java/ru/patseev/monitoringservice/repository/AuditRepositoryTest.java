package ru.patseev.monitoringservice.repository;

class AuditRepositoryTest {
//
//	private static AuditRepository auditRepository;
//
//	@BeforeAll
//	static void setUp() {
//		auditRepository = new AuditRepositoryImpl();
//	}
//
//	@Test
//	void mustSaveUser() {
//		auditRepository.save("test", new UserAction());
//
//		Mockito.verify(auditDatabase, Mockito.atLeastOnce())
//				.saveUserAction(Mockito.anyString(), Mockito.any(UserAction.class));
//	}
//
//	@Test
//	void mustFindUserActionByUsername() {
//		LocalDateTime registrationAt = LocalDateTime.now();
//		LocalDateTime logInAt = LocalDateTime.now();
//
//		List<UserAction> actual = new ArrayList<>() {{
//			add(new UserAction(registrationAt, ActionEnum.REGISTRATION));
//			add(new UserAction(logInAt, ActionEnum.LOG_IN));
//		}};
//
//		Mockito.when(auditDatabase.getUserActions("test"))
//				.thenReturn(actual);
//
//		List<UserAction> expected = auditDatabase.getUserActions("test");
//
//		AssertionsForClassTypes.assertThat(actual).isEqualTo(expected);
//	}
}