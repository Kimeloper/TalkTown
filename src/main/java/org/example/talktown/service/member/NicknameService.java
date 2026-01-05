package org.example.talktown.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class NicknameService {

    private final Random random = new Random();
    private final List<String> nicknames1 = List.of("귀여운", "깜찍한", "명량한", "미소짓는", "화난", "배고픈", "허기진", "졸린", "외로운", "하품하는", "억울한", "답답한", "용감한", "지혜로운", "수줍은");
    private final List<String> nicknames2 = List.of("강아지", "고양이", "토끼", "쿼카", "카피바라", "수달", "햄스터", "병아리", "나무늘보", "팽귄", "사슴", "돼지", "코끼리", "미어캣");

    public String randomNickname() {
        return nicknames1.get(random.nextInt(nicknames1.size())) + nicknames2.get(random.nextInt(nicknames2.size()));
    }
}
