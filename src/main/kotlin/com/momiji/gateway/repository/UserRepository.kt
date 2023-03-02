package com.momiji.gateway.repository

import com.momiji.gateway.repository.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : CrudRepository<UserEntity, Long>
