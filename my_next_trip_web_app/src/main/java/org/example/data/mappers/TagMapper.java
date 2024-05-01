package org.example.data.mappers;

import org.example.models.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements RowMapper<Tag> {
    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();
        //Compose a tag based upon the row returned in the result set. Column labels defined in sql.
        tag.setId(rs.getInt("tag_id"));
        tag.setName(rs.getString("tag_name"));
        return tag;
    }
}
