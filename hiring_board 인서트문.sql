
-- PAY_PER_HOUR 추가하기!!!!
    INSERT INTO HIRING_BOARD
    (HIRING_NO
    , STORE_ID
    , USER_ID
    , HIRING_CNT
    , HIRING_LIKE_CNT    
    , DEL_YN             
    , HIRING_STS         
    , HIRING_DATE        
    , HIRING_UPDT_DATE   
    , SERVICE_TYPE       
    , WORK_START_DATE    
    , WORK_END_DATE      
    , NEGOTIABLE_YN      
    , URGENCY_YN         
    , HIRING_TITLE       
    , HIRING_TEXT        
    , RGST_DATE          
    , UPDT_DATE          
    , RGST_ID            
    , UPDT_ID)        
VALUES
    (
    1
    , 1
    , 'tester'
    , 0
    , 0    
    , 'N'             
    , 'N'         
    , '2025-11-30'        
    , NULL /* HIRING_UPDT_DATE 수정 */
    , '편의점'       
    , '2025-12-01'    
    , '2025-12-01'      
    , 'Y'      
    , 'Y'         
    , '급구 gs석호중앙점'       
    , '급구합니다. 쿨계약시 오천원 더 얹어드립니다.'        
    , NOW() /* 함수 호출 명확화 */
    , NULL /* UPDT_DATE 수정 */         
    , 'tester'            
    , NULL /* UPDT_ID 수정 */
    );
    
    commit;
    