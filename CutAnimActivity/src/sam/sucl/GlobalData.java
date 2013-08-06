package sam.sucl;

public class GlobalData 
{
//	public class CSubject
//	{
		public int MSubjectId;
		public String MSubjectBody;
		public String MAnswer;
		public int MIsCorrected;
		
		public GlobalData(int subjectId,String subjectBody,String answer,int isCorrected)
		{
			this.MSubjectId = subjectId;
			this.MSubjectBody = subjectBody;
			this.MAnswer = answer;
			this.MIsCorrected = isCorrected;
		}
		
		public GlobalData()
		{
			// Do nothing this method.
		}
		
		public void SetAttributes(int subjectId,String subjectBody,String answer,int isCorrected)
		{
			this.MSubjectId = subjectId;
			this.MSubjectBody = subjectBody;
			this.MAnswer = answer;
			this.MIsCorrected = isCorrected;
		}
//	}

}
