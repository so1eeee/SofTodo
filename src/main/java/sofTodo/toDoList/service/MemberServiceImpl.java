package sofTodo.toDoList.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import sofTodo.toDoList.domain.Member;
import sofTodo.toDoList.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public void addMember(String username, String password, String nickname) {
        Member member = new Member();
        member.setNickname(nickname);
        member.setUsername(username);
        member.setPassword(password);
        memberRepository.save(member);
    }

    @Override
    public void findAll(Model model) {
        List<Member> result = memberRepository.findAll();
        model.addAttribute("members", result);
    }
}