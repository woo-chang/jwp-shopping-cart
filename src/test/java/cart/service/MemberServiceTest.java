package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import cart.dao.MemberDao;
import cart.dto.response.MemberResponse;
import cart.entity.MemberEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberDao memberDao;

    @Test
    @DisplayName("모든 멤버를 조회한다.")
    void findMembers() throws JsonProcessingException {
        final MemberEntity memberA = new MemberEntity(1L, "a@naver.com", "password1");
        final MemberEntity memberB = new MemberEntity(2L, "b@naver.com", "password2");

        given(memberDao.findAll()).willReturn(List.of(memberA, memberB));

        final List<MemberResponse> result = memberService.findMembers();

        final List<MemberResponse> expected = List.of(
                MemberResponse.from(memberA),
                MemberResponse.from(memberB)
        );
        assertThat(objectMapper.writeValueAsString(result)).isEqualTo(objectMapper.writeValueAsString(expected));
    }
}
