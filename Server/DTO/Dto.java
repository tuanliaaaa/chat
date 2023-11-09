package DTO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dto implements Serializable{
    Map<String,Object> Header;
    Map<String,Object> Body;
}
