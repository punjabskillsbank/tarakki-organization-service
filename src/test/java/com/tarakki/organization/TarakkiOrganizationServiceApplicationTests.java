package com.tarakki.organization;

import com.tarakki.organization.repository.MemberRepository;
import com.tarakki.organization.repository.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(properties = {
		"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration"
})
class TarakkiOrganizationServiceApplicationTests {

	@MockitoBean
	private OrganizationRepository organizationRepository;

	@MockitoBean
	private MemberRepository memberRepository;

	@Test
	void contextLoads() {
	}

}
