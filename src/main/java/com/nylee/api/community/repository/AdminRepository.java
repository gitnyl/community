package com.nylee.api.community.repository;

import com.nylee.api.common.repository.model.MngAdmin;
import com.nylee.api.community.model.request.admin.ReqAdminSearch;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@Mapper
public interface AdminRepository {

    List<MngAdmin> getAdminList(ReqAdminSearch requestAdminSearch) throws SQLException;

}
