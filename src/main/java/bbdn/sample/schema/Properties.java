package bbdn.sample.schema;

import blackboard.data.AbstractIdentifiable;
import blackboard.persist.DataType;
import blackboard.persist.impl.mapping.annotation.Column;
import blackboard.persist.impl.mapping.annotation.PrimaryKey;
import blackboard.persist.impl.mapping.annotation.Table;

@Table("bbdn_props")
public class Properties  extends AbstractIdentifiable {

		   public static final DataType DATA_TYPE = new DataType( Properties.class );
		   
		   public DataType getDataType() {
				return DATA_TYPE;
		   }
		   
		   /*
		    * BBLEARN-# \d public.bbdn_props
               Table "public.bbdn_props"
				      Column       |          Type          | Modifiers 
				-------------------+------------------------+-----------
				 pk1               | integer                | not null
				 props_enabled_ind | character(1)           | not null
				 props_msg         | character varying(256) | 
				 props_status      | integer                | not null
				 props_user_id     | integer                | 
		    */

		   @PrimaryKey
		   private int pk1;
		  
		   @Column(value = "props_enabled_ind")
		   private String enabled;
		   
		   @Column(value = "props_msg")
		   private String message;
		   
		   @Column(value = "props_status")
		   private int status;
		   
		   @Column(value = "props_user_id")
		   private int userId;

		   /**
			 * @return the props_id
			 */
			public int getPropertyId() {
				return pk1;
			}
			
		/**
		 * @return enabled
		 */
		public Boolean isEnabled() {
			if ( this.enabled.equals("Y") ) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * @param flag : If true, set enabled to 'Y', else 'N'
		 */
		public void enable(Boolean flag) {
			if (flag) {
				this.enabled = "Y";
			} else {
				this.enabled = "N";
			}
			
		}
		
		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * @param message the message to set
		 */
		public void setMessage(String newMessage) {
			this.message = newMessage;
		}
		
		/**
		 * @return the status
		 */
		public int getStatus() {
			return status;
		}

		/**
		 * @param status the props_links to set
		 */
		public void setStatus(int newStatus) {
			this.status = newStatus;
		}  
		
		/**
		 * @return the user Id
		 */
		public int getUserId() {
			return userId;
		}

		/**
		 * @param status the props_links to set
		 */
		public void setUserId(int newUserId) {
			this.userId = newUserId;
		}  
}
