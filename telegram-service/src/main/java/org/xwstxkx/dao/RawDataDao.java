package org.xwstxkx.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.xwstxkx.entity.RawData;

@Repository
public interface RawDataDao extends JpaRepository<RawData, Long> {

}
