package com.kkkssh.community.repository;

import com.kkkssh.community.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    //UPDATE board_table SET board_hits = board_hits + 1 WHERE id = ?
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
                                                                                 //ㄴ@Param("id")와 매칭
    void updateHits(@Param("id") Long id);
}
