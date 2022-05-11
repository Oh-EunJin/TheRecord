package com.record.the_record.diary.service;

import com.record.the_record.diary.dto.DiaryDetailDto;
import com.record.the_record.diary.dto.DiaryDto;
import com.record.the_record.diary.dto.DiaryTitleDto;
import com.record.the_record.diary.repository.DiaryRepository;
import com.record.the_record.entity.Diary;
import com.record.the_record.entity.Folder;
import com.record.the_record.entity.User;
import com.record.the_record.entity.enums.Category;
import com.record.the_record.entity.enums.VisibleStatus;
import com.record.the_record.folder.repository.FolderRepository;
import com.record.the_record.user.repository.UserRepository;
import com.record.the_record.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final FolderRepository folderRepository;

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Diary addDiary(DiaryDto diaryDto) {

        VisibleStatus getVisible = VisibleStatus.valueOf(diaryDto.getVisible());
        Category getCategory = Category.valueOf(diaryDto.getCategory());
        Long userPk = userService.currentUser();
        User user = userRepository.findByPk(userPk);
        Folder folder = folderRepository.findOneById(diaryDto.getFolderId());

        Diary diary = Diary.builder()
                .title(diaryDto.getTitle())
                .content(diaryDto.getContent())
                .category(getCategory)
                .visibleStatus(getVisible)
                .mediaUrl(diaryDto.getMediaUrl())
                .recordDt(LocalDateTime.now())
                .folder(folder)
                .user(user)
                .build();

        return diaryRepository.save(diary);
    }

    @Override
    public List<DiaryDetailDto> findDiaryList(Long userPk, int page) {
        int size = 10;

        Long loginUser = userService.currentUser();
        User host = userRepository.findByPk(userPk);
        VisibleStatus visibleStatus = VisibleStatus.valueOf("PUBLIC");
        Pageable pageable = PageRequest.of(page, size);

        Page<Diary> diaryList;
        List<DiaryDetailDto> diaryDtoList = new ArrayList<>();

        if(loginUser != userPk) {
            diaryList = diaryRepository.findByUserAndVisibleStatusOrderByRecordDtDesc(pageable, host, visibleStatus);
        } else {
            diaryList = diaryRepository.findByUser_PkOrderByRecordDtDesc(pageable, userPk);
        }

        for (Diary diary : diaryList) {
            diaryDtoList.add(DiaryDetailDto.builder()
                    .diaryId(diary.getId())
                    .folderId(diary.getFolder().getId())
                    .title(diary.getTitle())
                    .content(diary.getContent())
                    .category(String.valueOf(diary.getCategory()))
                    .mediaUrl(diary.getMediaUrl())
                    .recordDt(String.valueOf(diary.getRecordDt()).substring(0,10))
                    .visible(String.valueOf(diary.getVisibleStatus()))
                    .build());
        }

        return diaryDtoList;
    }

    @Override
    public List<DiaryTitleDto> findDiaryTitleList(Long userPk, Long folderId) {

        Long loginUser = userService.currentUser();
        User host = userRepository.findByPk(userPk);
        VisibleStatus visibleStatus = VisibleStatus.valueOf("PUBLIC");
        Folder folder = folderRepository.findOneById(folderId);

        List<Diary> diaryList;
        List<DiaryTitleDto> diaryDtoList = new ArrayList<>();

        if(loginUser != userPk) {
            diaryList = diaryRepository.findByUserAndVisibleStatusAndFolder(host, visibleStatus, folder);
        } else {
            diaryList = diaryRepository.findByUser_PkAndFolder(userPk, folder);
        }

        for (Diary diary : diaryList) {
            diaryDtoList.add(DiaryTitleDto.builder()
                    .diaryId(diary.getId())
                    .title(diary.getTitle())
                    .recordDt(String.valueOf(diary.getRecordDt()).substring(0,10))
                    .visible(String.valueOf(diary.getVisibleStatus()))
                    .build());
        }

        return diaryDtoList;
    }

    @Override
    public List<DiaryDetailDto> findDiaryDateList(Long userPk, String date) {

        Long loginUser = userService.currentUser();
        User host = userRepository.findByPk(userPk);
        VisibleStatus visibleStatus = VisibleStatus.valueOf("PUBLIC");
        LocalDateTime startDate = LocalDateTime.of(LocalDate.parse(date), LocalTime.of(0,0,0));
        LocalDateTime endDate = LocalDateTime.of(LocalDate.parse(date), LocalTime.of(23,59,59));

        List<Diary> diaryList;
        List<DiaryDetailDto> diaryDtoList = new ArrayList<>();

        if(loginUser != userPk) {
            diaryList = diaryRepository.findByUserAndVisibleStatusAndRecordDtBetween(host, visibleStatus, startDate, endDate);
        } else {
            diaryList = diaryRepository.findByUser_PkAndRecordDtBetween(userPk, startDate, endDate);
        }

        for (Diary diary : diaryList) {
            diaryDtoList.add(DiaryDetailDto.builder()
                    .diaryId(diary.getId())
                    .folderId(diary.getFolder().getId())
                    .title(diary.getTitle())
                    .content(diary.getContent())
                    .category(String.valueOf(diary.getCategory()))
                    .mediaUrl(diary.getMediaUrl())
                    .recordDt(date)
                    .visible(String.valueOf(diary.getVisibleStatus()))
                    .build());
        }

        return diaryDtoList;
    }

    @Override
    public DiaryDetailDto findDiaryDetail(Long diaryId) {

        Diary diary = diaryRepository.findOneById(diaryId);

        DiaryDetailDto diaryDetailDto = DiaryDetailDto.builder()
                .diaryId(diary.getId())
                .folderId(diary.getFolder().getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .category(String.valueOf(diary.getCategory()))
                .mediaUrl(diary.getMediaUrl())
                .recordDt(String.valueOf(diary.getRecordDt()).substring(0,10))
                .visible(String.valueOf(diary.getVisibleStatus()))
                .build();

        return diaryDetailDto;
    }
}
