package com.yeogiya.web.service;

import com.yeogiya.entity.diary.*;
import com.yeogiya.entity.member.LoginType;
import com.yeogiya.entity.member.Member;
import com.yeogiya.entity.member.Role;
import com.yeogiya.enumerable.EnumErrorCode;
import com.yeogiya.exception.ClientException;
import com.yeogiya.repository.*;
import com.yeogiya.web.YeogiyaApplicationTest;
import com.yeogiya.web.auth.PrincipalDetails;
import com.yeogiya.web.diary.dto.request.DiarySaveRequestDTO;
import com.yeogiya.web.diary.dto.request.PlaceRequestDTO;
import com.yeogiya.web.diary.dto.response.DiaryIdResponseDTO;
import com.yeogiya.web.diary.dto.response.DiaryResponseDTO;
import com.yeogiya.web.diary.service.DiaryService;
import com.yeogiya.web.handler.CustomLogoutHandler;
import com.yeogiya.web.handler.CustomLogoutSuccessHandler;
import com.yeogiya.web.jwt.JwtService;
import com.yeogiya.web.member.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import static org.assertj.core.api.Assertions.assertThat;



import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

@ContextConfiguration(classes = YeogiyaApplicationTest.class)
@ActiveProfiles("local")
@SpringBootTest
public class DiaryServiceTest {


    @Autowired
    private DiaryService diaryService;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private HashtagRepository hashtagRepository;

    @Autowired
    private DiaryHashtagRepository diaryHashtagRepository;
    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    @DisplayName("다이어리를 등록한다.")
//    @Transactional (propagation = Propagation.SUPPORTS)
    public void postDiaryTest() throws Exception {
        // given
        Member member = Member.builder()
            .id("springboot")
            .password("springboot")
            .email("springboot@gmail.com")
            .nickname("스프링부트")
            .loginType(LoginType.EMAIL)
            .role(Role.USER)
            .build();
        PrincipalDetails principalDetails = new PrincipalDetails(member);
        String content = "방가방가방가";
        String hashtag3 = "#바부";
        int kakaoId = 1004;
        DiarySaveRequestDTO diarySaveRequestDTO = DiarySaveRequestDTO.builder()
            .content(content)
            .openYn(OpenYn.Y)
            .star(5.0)
            .hashtags(Arrays.asList("#샵1", "#샵", hashtag3))
            .build();
        PlaceRequestDTO placeRequestDTO = PlaceRequestDTO.builder()
            .name("하이루")
            .address("서울시 무슨구 무슨동")
            .kakaoId(kakaoId)
            .latitude(37.5759)
            .longitude(126.9768)
            .build();

        MockMultipartFile img1 = new MockMultipartFile("image", "img1.png", "png", new FileInputStream("src/test/resources/image/img1.jpg"));
        MockMultipartFile img2 = new MockMultipartFile("image", "img2.png", "png", new FileInputStream("src/test/resources/image/img2.jpg")
        );
        List<MultipartFile> multipartFiles = Arrays.asList(img1, img2);

        // when
//        DiaryIdResponseDTO diaryIdResponseDTO = diaryService.postDiary(diarySaveRequestDTO, placeRequestDTO, principalDetails, multipartFiles);

        // then
//        assertThat(diaryIdResponseDTO.getId()).isGreaterThan(0L);

//        Diary diary = diaryRepository.findById(diaryIdResponseDTO.getId()).orElseThrow(() -> new ClientException.BadRequest(EnumErrorCode.NOT_FOUND_DIARY));
//        assertThat(diary.getContent()).isEqualTo(content);

        List<Hashtag> hashtags = hashtagRepository.findAll();
        assertThat(hashtags.get(hashtags.size() - 1).getName()).isEqualTo(hashtag3);

        // List<DiaryHashtag> diaryHashtags = diaryHashtagRepository.findByDiaryId(diaryIdResponseDTO.getId());
        // assertThat(diaryHashtags.get(diaryHashtags.size() - 1).getHashtag().getName()).isEqualTo(hashtag3);

        List<Place> places = placeRepository.findAll();
        assertThat(places.get(places.size() - 1).getKakaoId()).isEqualTo(kakaoId);
    }
}
