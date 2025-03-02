package com.example.todolist.connection_test;

import com.example.todolist.db.UserRepository;
import com.example.todolist.dbtest.Memo;
import com.example.todolist.dbtest.MemoRepository;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    void InsertDummies() {
        IntStream.rangeClosed(1,5).forEach(i -> {
            Memo memo = Memo.builder()
                    .memoText("Sample..." + i)
                    .build();
            //create!!
            memoRepository.save(memo);
        });
    }

    @Test
    void SelectDummies() {
        Long id = 26L;

        Optional<Memo> result = memoRepository.findById(id);
        System.out.println("=============================");

        if(result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }

    }

    @Test
    void UpdateDummies() {
        Memo memo = Memo.builder()
                        .id(30L)
                        .memoText("Update Text")
                        .build();
        System.out.println("memoRepository.count() = " + memoRepository.count());
        memoRepository.save(memo);
    }

    @Test
    void DeleteDummies() {
        Long id = 30L;
//        memoRepository.deleteAll();
        memoRepository.deleteById(id);
    }

    @Test
    void createdtuple() {
        Memo memo = Memo.builder().memoText("It's active to we go").build();
        System.out.println("memo.getMemoText() = " + memo.getMemoText());
        System.out.println("memo.getId() = " + memo.getId());
        memoRepository.save(memo);
    }
}
