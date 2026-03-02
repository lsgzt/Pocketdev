package com.pocketdev.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pocketdev.data.local.entity.ProjectEntity
import com.pocketdev.model.ProgrammingLanguage
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    
    @Query("SELECT * FROM projects ORDER BY modifiedAt DESC")
    fun getAllProjects(): Flow<List<ProjectEntity>>
    
    @Query("SELECT * FROM projects ORDER BY modifiedAt DESC")
    fun getAllProjectsLive(): LiveData<List<ProjectEntity>>
    
    @Query("SELECT * FROM projects WHERE id = :id")
    suspend fun getProjectById(id: Long): ProjectEntity?
    
    @Query("SELECT * FROM projects WHERE language = :language ORDER BY modifiedAt DESC")
    fun getProjectsByLanguage(language: ProgrammingLanguage): Flow<List<ProjectEntity>>
    
    @Query("SELECT * FROM projects WHERE name LIKE '%' || :query || '%' ORDER BY modifiedAt DESC")
    fun searchProjects(query: String): Flow<List<ProjectEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ProjectEntity): Long
    
    @Update
    suspend fun updateProject(project: ProjectEntity)
    
    @Delete
    suspend fun deleteProject(project: ProjectEntity)
    
    @Query("DELETE FROM projects WHERE id = :id")
    suspend fun deleteProjectById(id: Long)
    
    @Query("SELECT COUNT(*) FROM projects")
    suspend fun getProjectCount(): Int
    
    @Query("UPDATE projects SET code = :code, modifiedAt = :modifiedAt WHERE id = :id")
    suspend fun updateProjectCode(id: Long, code: String, modifiedAt: Long = System.currentTimeMillis())
}
