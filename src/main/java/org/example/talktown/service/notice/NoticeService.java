package org.example.talktown.service.notice;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.talktown.domain.Notice;
import org.example.talktown.dto.notice.NoticeRequest;
import org.example.talktown.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    //create
    public Notice save(NoticeRequest noticeRequest){
        return noticeRepository.save(noticeRequest.toEntity());
    }

    //findAll
    public Page<Notice> findAll(Pageable pageable){
        return noticeRepository.findAll(pageable);
    }

    public List<Notice> getFiveBoards(){
        return noticeRepository.findTop5ByOrderByCreatedAtDescIdDesc();
    }

    //search
    public  Page<Notice> searchNotice(String type, String keyword, Pageable pageable){
        if(type.equals("title")){
            return noticeRepository.findByTitleContaining(keyword,pageable);
        }else if(type.equals("author")){
            return noticeRepository.findByAuthorContaining(keyword, pageable);
        }else if(type.equals("content")){
            return noticeRepository.findByContentContaining(keyword, pageable);
        }else{
            return noticeRepository.findAll(pageable);
        }
    }

    //findById
    public Notice findById(long id){
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("error: " + id + "번째 게시물을 찾을 수 었습니다."));
        notice.views();
        return noticeRepository.save(notice);
    }

    //delete
    public void delete(long id){
        noticeRepository.deleteById(id);
    }

    //update
    @Transactional
    public Notice update(long id, NoticeRequest noticeRequest){
        Notice notice = noticeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("error: " + id + "번째 게시물을 찾을 수 었습니다."));
        notice.update(noticeRequest.getTitle(), noticeRequest.getContent());
        return notice;
    }
}
